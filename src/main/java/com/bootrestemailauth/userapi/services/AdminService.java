package com.bootrestemailauth.userapi.services;

import com.bootrestemailauth.userapi.dao.AdminAccDetailsDao;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.AdminAccountDetails;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.UserRequest;
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

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public UserDao userDao;

    @Autowired
    public ResponseMessage responseMessage;

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

    public ResponseEntity<?> reportuser(String authorization,String user_email){
        try {
            userRequest = userDao.getUserRequestByuseremail(user_email);
            if(userRequest==null){
                responseMessage.setMessage("User Does not exist..Please enter correct user email id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            userRequest.setRed_flag_count(userRequest.getRed_flag_count()+1);
            userDao.save(userRequest);
            responseMessage.setMessage("Thank you for reporting...We will look into this matter");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}

