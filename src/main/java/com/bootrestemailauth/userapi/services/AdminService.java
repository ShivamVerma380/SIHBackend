package com.bootrestemailauth.userapi.services;

import com.bootrestemailauth.userapi.dao.AdminAccDetailsDao;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.entities.AdminAccountDetails;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminService {
    
    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public AdminRequest adminRequest;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public AdminAccountDetails adminAccountDetails;

    @Autowired
    public AdminAccDetailsDao accDetailsDao;

    public ResponseEntity<?> getMonumentsByadminId(String authorization){

        try {
            String jwtToken = authorization.substring(7);
            String reg_email = jwtUtil.extractUsername(jwtToken);

            adminRequest = adminDao.getAdminRequestByemail(reg_email);

            if(adminRequest==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // List<AdminRequest> answer= (List<AdminRequest>) adminDao.getAdminRequestByemail(reg_email);
            // if(answer.isEmpty()){
            //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // }   
            List<MonumentRequest> answer = monumentDao.getMonumentRequestByadminId(adminRequest.getId());
            if(answer.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        

    }

    public ResponseEntity<?> getAccountDetails(String authorization){
        try {
            String jwtToken = authorization.substring(7);
            String reg_email = jwtUtil.extractUsername(jwtToken);
            adminRequest = adminDao.getAdminRequestByemail(reg_email);
            if(adminRequest==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            adminAccountDetails = accDetailsDao.getAdminAccountDetailsByadminId(adminRequest.getId());
            return ResponseEntity.ok(adminAccountDetails);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

