package com.bootrestemailauth.userapi.services;

import java.sql.Blob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.bootrestemailauth.userapi.helper.LobHelper;
import com.bootrestemailauth.userapi.helper.VerifyMonumentHelper;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentVerificationRequestDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentVerificationRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VerifyMonumentService {
    
    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public MonumentVerificationRequest monumentVerificationRequest;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public MonumentVerificationRequestDao monumentVerificationRequestDao;

    @Autowired
    public AdminRequest adminRequest;


    @Autowired
    public LobHelper lobHelper;

    @Autowired
    public AdminDao adminDao;

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    public VerifyMonumentHelper verifyMonumentHelper;

    

    public ResponseEntity<?> verifyMonument(String authorization,String monument_name,String website, MultipartFile monumentImage,String monument_location, String admin_phone,MultipartFile monument_poa,String admin_aadhar,String monument_type){

        try {

            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);

            adminRequest = adminDao.getAdminRequestByemail(registered_email);
            if(adminRequest==null){
                responseMessage.setMessage("Admin Does not exist with corressponding token");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }

            Session session = entityManager.unwrap(Session.class);
            Blob monumentImageData = session.getLobHelper().createBlob(monumentImage.getInputStream(),monumentImage.getSize());

            //Session session2 = entityManager.unwrap(Session.class);

            Blob monumentPoaData = session.getLobHelper().createBlob(monument_poa.getInputStream(),monument_poa.getSize());
            monumentVerificationRequest.setAdminId(adminRequest.getId());
            monumentVerificationRequest.setMonument_image(monumentImageData);    
            monumentVerificationRequest.setPower_of_attorney(monumentPoaData);
            monumentVerificationRequest.setAdminPhoneNo(admin_phone);
            monumentVerificationRequest.setAdmin_aadhar(admin_aadhar);
            monumentVerificationRequest.setMonumentName(monument_name);
            monumentVerificationRequest.setMonument_location(monument_location);
            monumentVerificationRequest.setWebsiteLink(website);
            monumentVerificationRequest.setMonumentType(monument_type);
            monumentVerificationRequest.setUpdate_status("Verification Under Progress"); 

            monumentVerificationRequestDao.save(monumentVerificationRequest);

            //email with attachment

            if(verifyMonumentHelper.isEmailSent(monument_name, website, monumentImage, monument_location, admin_phone, monument_poa, admin_aadhar,monument_type)){
                responseMessage.setMessage("Monument Verification is in progress.We will update your verification status soon!! and email sent");
            }else{
                responseMessage.setMessage("Monument Verification is in progress.We will update your verification status soon!! and email not sent");
            }

            
            //return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
