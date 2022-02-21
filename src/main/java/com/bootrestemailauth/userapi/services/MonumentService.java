package com.bootrestemailauth.userapi.services;

import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.MonumentVerificationRequestDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.MonumentVerificationRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.helper.FileUploadHelper;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.helper.LobHelper;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class MonumentService {
    
    @Autowired
    public ResponseMessage responseMessage;
    
    @Autowired
    public AdminDao adminDao;
    
    @Autowired
    public AdminRequest adminRequest;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public MonumentRequest monument;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public FileUploadHelper fileUploadHelper;

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public MonumentVerificationRequest monumentVerificationRequest;

    @Autowired
    public MonumentVerificationRequestDao monumentVerificationRequestDao;



    @Autowired
    public LobHelper lobHelper;

    public ResponseEntity<?> getAllMonuments(){
        try {
            List<MonumentRequest> monuments = (List<MonumentRequest>) monumentDao.findAll();
            if(monuments==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(monuments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }

    public ResponseEntity<?> getMonumentsByType(String type){
        try {
            List<MonumentRequest> monuments = (List<MonumentRequest>) monumentDao.findAll();
            if(monuments==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            List<MonumentRequest> answer = new ArrayList<>();
            for(int i=0;i<monuments.size();i++){
                if(monuments.get(i).getMonumentType().equalsIgnoreCase(type)){
                    answer.add(monuments.get(i));
                }
            }
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }


    public ResponseEntity<?> addMonument(String authorization,String monument_name, String website,MultipartFile monumentImage, String monument_location, MultipartFile monument_poa, String monument_type,String admin_aadhar,String admin_phone){

        try {
            
            
            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);

            adminRequest = adminDao.getAdminRequestByemail(registered_email);

            if(adminRequest==null){
                responseMessage.setMessage("User is not allowed to access this!!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }

            //Step 1: admin phone and aadhar set in admin reg table;
            adminRequest.setAadhar_number(admin_aadhar);
            adminRequest.setPhoneNos(admin_phone);

            adminDao.save(adminRequest);

            //Step 2: Add info in monument table
            // int adminID =adminRequest.getId();
            // monument.setAdminId(adminID);

            // monument.setMonumentName(monument_name);
            // monument.setMonumentType(monument_type);
            // monument.setWebsiteLink(website);
            // monument.setMonumentLocation(monument_location);

            

            Session session = entityManager.unwrap(Session.class);
            Blob monumentPoaData = session.getLobHelper().createBlob(monument_poa.getInputStream(),monument_poa.getSize());
            //monument.setMonumentPOA(monumentPoaData);

            //2a: Id code
             
            //2b: File upload and urls store
            String monumentImgUrl ;
            if(fileUploadHelper.isMonumentFileUploaded(monumentImage, monument_name, "image")){
                String ext = monumentImage.getOriginalFilename();
                int i=0;
                for(;i<ext.length();i++){
                    if(ext.charAt(i)=='.') break;
                }
    
                ext = ext.substring(i+1);
    
                monumentImgUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/monument/").path(monument_name).path("_").path("image").path(".").path(ext).toUriString();

                
                
                monument.setMonumentImageUrl(monumentImgUrl);
                


            }else{
                responseMessage.setMessage("Problem in uploading images");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }

            MonumentRequest mon = new MonumentRequest(adminRequest.getId(), monument_name, monumentImgUrl, monumentPoaData, website, monument_type, monument_location);
            monumentDao.save(mon);

            //Step 3: Monument Verification Table se delete karna hai

            monumentVerificationRequest = monumentVerificationRequestDao.getMonumentVerificationRequestByadminId(adminRequest.getId());
            monumentVerificationRequestDao.delete(monumentVerificationRequest);
        

            responseMessage.setMessage("Monument verified and added successfully");
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }

    public ResponseEntity<?> addmonumentInfo(String authorization,String monument_name,MultipartFile video,Time opening_time, Time closing_time , String description,double indian_adult, double indian_child,double foreign_adult, double foreign_child,String closed_day){

        try {

            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);
            adminRequest = adminDao.getAdminRequestByemail(registered_email);

            monument = monumentDao.getMonumentRequestBymonumentName(monument_name);

            if(adminRequest.getId()!=monument.getAdminId()){
                responseMessage.setMessage("You don't have permission to chnage this monument information");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }

            //video url generation
            if(fileUploadHelper.isMonumentFileUploaded(video, monument_name, "video")){
                String ext = video.getOriginalFilename();
                int i=0;
                for(;i<ext.length();i++){
                    if(ext.charAt(i)=='.') break;
                }
    
                ext = ext.substring(i+1);
    
                String monumentVideoUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/monument/").path(monument_name).path("_").path("video").path(".").path(ext).toUriString();

                
                
                monument.setMonumentPreviewUrl(monumentVideoUrl);
            }

            
            monument.setClosedDay(closed_day);
            monument.setClosingTime(closing_time);
            monument.setOpeningTime(opening_time);
            monument.setIndianAdultFare(indian_adult);
            monument.setIndianChildFare(indian_child);
            monument.setForeignAdultFare(foreign_adult);
            monument.setForeignChildFare(foreign_child);
            monument.setMonumentDescription(description);

            monumentDao.save(monument); //for update and creating monument save func is used for dao

            responseMessage.setMessage("Information saved successfully!!");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }

    }

}
