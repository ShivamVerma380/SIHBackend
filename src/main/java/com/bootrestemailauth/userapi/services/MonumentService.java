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
import com.bootrestemailauth.userapi.entities.monumentResponse;
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

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public MonumentVerificationRequest monumentVerificationRequest;

    @Autowired
    public MonumentVerificationRequestDao monumentVerificationRequestDao;

    @Autowired
    public monumentResponse mresponse;

    @Autowired
    public LobHelper lobHelper;

    public ResponseEntity<?> getAllMonuments(){
        try {
            List<MonumentRequest> monuments = (List<MonumentRequest>) monumentDao.findAll();
            List<monumentResponse> mList = new ArrayList<>();
            if(monuments==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            System.out.println(monuments.size());
            for(int i=0;i<monuments.size();i++){
                int blobLength = (int) monuments.get(i).getMonumentImage().length();
                byte[] blobAsBytes = monuments.get(i).getMonumentImage().getBytes(1, blobLength);
                // System.out.println(monuments.get(i).getMonumentName());
                monumentResponse mResponse = new monumentResponse();
                mResponse.setMonumentName(monuments.get(i).getMonumentName()); 
                mResponse.setMonumentImg(blobAsBytes);
                blobLength = (int) monuments.get(i).getMonumentPreview().length();
                // blobAsBytes = monuments.get(i).getMonumentPreview().getBytes(1, blobLength);
                // mResponse.setMonumentVideo(blobAsBytes); 
                mResponse.setClosedDay(monuments.get(i).getClosedDay());
                mResponse.setForeign_adult(monuments.get(i).getForeignAdultFare());
                mResponse.setForeign_child(monuments.get(i).getForeignChildFare());
                mResponse.setIndian_adult(monuments.get(i).getIndianAdultFare());
                mResponse.setIndian_child(monuments.get(i).getIndianChildFare());
                mResponse.setLocation(monuments.get(i).getMonumentLocation());
                mResponse.setMonumentDesc(monuments.get(i).getMonumentDescription());
                mResponse.setMonumentLink(monuments.get(i).getWebsiteLink());
                mResponse.setStartTime(monuments.get(i).getOpeningTime());
                mResponse.setCloseTime(monuments.get(i).getClosingTime());
                mList.add(mResponse);
                // System.out.println(mList.get(i));
            }
            for(int i = 0; i< mList.size(); i++){
                System.out.println(mList.get(i).getMonumentName());
            }
            return ResponseEntity.ok(mList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }

    public ResponseEntity<?> getMonumentVideo(String name){
        try {
            MonumentRequest monumentRequest = monumentDao.getMonumentRequestBymonumentName(name);
            if(monumentRequest!=null){
                int blobLength = (int) monumentRequest.getMonumentPreview().length();
                byte[] blobAsBytes = monumentRequest.getMonumentPreview().getBytes(1, blobLength);
                mresponse.setMonumentVideo(blobAsBytes);
                
            }
            return ResponseEntity.ok(mresponse); 
            
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
                    int blobLength = (int) monuments.get(i).getMonumentImage().length();
                    byte[] blobAsBytes = monuments.get(i).getMonumentImage().getBytes(1, blobLength);
                    
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
            // monument.setMonumentPOA(monumentPoaData);

            //2a: Id code
             
            //2b: File upload and urls store
            Blob monumentImgData = session.getLobHelper().createBlob(monumentImage.getInputStream(),monumentImage.getSize());
            // Blob monumentPreviewData = session.getLobHelper().createBlob(monumentImage.getInputStream(),monumentImage.getSize());
            
            MonumentRequest mon = new MonumentRequest(adminRequest.getId(), monument_name, monumentImgData, monumentPoaData, website, monument_type, monument_location);
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
            Session session=entityManager.unwrap(Session.class);
            Blob monumentPreviewData = session.getLobHelper().createBlob(video.getInputStream(),video.getSize());
            monument.setMonumentPreview(monumentPreviewData);
            
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