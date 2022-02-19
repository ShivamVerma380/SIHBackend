package com.bootrestemailauth.userapi.services;

import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentVerificationRequestDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentVerificationRequest;
import com.bootrestemailauth.userapi.entities.MonumentVerificationResponse;
import com.bootrestemailauth.userapi.helper.JwtUtil;

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
    public MonumentVerificationResponse monumentVerificationResponse;

    @Autowired
    public MonumentVerificationRequestDao monumentVerificationRequestDao;

    @Autowired
    public AdminRequest adminRequest;


    @Autowired
    public AdminDao adminDao;

    

    public ResponseEntity<?> verifyMonument(String authorization,String monument_name,String website, MultipartFile monumentImage,String monument_location, String admin_phone,MultipartFile monument_poa,String admin_aadhar){

        try {

            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);

            adminRequest = adminDao.getAdminRequestByemail(registered_email);
            if(adminRequest==null){
                monumentVerificationResponse.setStatus("Admin Does not exist with corressponding token");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(monumentVerificationResponse);
            }

            monumentVerificationRequest.setAdminId(adminRequest.getId());
                        


            
            
        } catch (Exception e) {

        }
        return null;
    }

}
