package com.bootrestemailauth.userapi.controllers;

import javax.validation.constraints.Email;

import com.bootrestemailauth.userapi.dao.BlockedUsersDao;
import com.bootrestemailauth.userapi.entities.BlockedUsers;
import com.bootrestemailauth.userapi.entities.OtpResponse;
import com.bootrestemailauth.userapi.services.EmailVerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    
    @Autowired
    public EmailVerificationService emailVerificationService;

    @Autowired
    public BlockedUsers blockedUsers;

    @Autowired
    public BlockedUsersDao blockedUsersDao;

    @Autowired
    public OtpResponse otpResponse;

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Email @RequestParam("email") String email){
        try {
            blockedUsers = blockedUsersDao.getBlockedUsersByemail(email);
            if(blockedUsers!=null){
                otpResponse.setMessage("This email id has been blocked due to fake booking of tickets...");
                otpResponse.setOtp(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(otpResponse);
            }
            return emailVerificationService.verifyEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponse.setMessage(e.getMessage());
            otpResponse.setOtp(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otpResponse);
        }
        
    }

    


}