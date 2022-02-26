package com.bootrestemailauth.userapi.services;

import com.bootrestemailauth.userapi.config.MySecurityConfig;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.OtpResponse;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public OtpResponse otpResponse;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public AdminRequest adminRequest;

    @Autowired
    public UserDao  userDao;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public EmailVerificationService emailVerificationService;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    public ResponseEntity<?> forgotPassword(String authorization){
        try {
            String jwtToken = authorization.substring(7);
            String email_registered = jwtUtil.extractUsername(jwtToken);
            return emailVerificationService.verifyEmail(email_registered);

        } catch (Exception e) {
            e.printStackTrace();
            otpResponse.setMessage(e.getMessage());
            otpResponse.setOtp(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otpResponse);
        }
        
    }

    public ResponseEntity<?> updatePassword(String authorization,String password){
        try {
            String jwtToken = authorization.substring(7);
            String email_reg = jwtUtil.extractUsername(jwtToken);
            userRequest = userDao.getUserRequestByuseremail(email_reg);
            if(userRequest!=null){
                String new_encoded_password = mySecurityConfig.passwordEncoder().encode(password);
                userRequest.setPassword(new_encoded_password);
                userDao.save(userRequest);
                responseMessage.setMessage("Password updated successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            adminRequest = adminDao.getAdminRequestByemail(email_reg);
            String new_encoded_password = mySecurityConfig.passwordEncoder().encode(password);
            adminRequest.setPassword(new_encoded_password);
            adminDao.save(adminRequest);
            responseMessage.setMessage("Password updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    
}
