package com.bootrestemailauth.userapi.controllers;

import javax.validation.constraints.Email;

import com.bootrestemailauth.userapi.entities.OtpResponse;
import com.bootrestemailauth.userapi.services.EmailVerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    
    @Autowired
    public EmailVerificationService emailVerificationService;

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Email @RequestParam("email") String email){
        
        return emailVerificationService.verifyEmail(email);
    }


}
