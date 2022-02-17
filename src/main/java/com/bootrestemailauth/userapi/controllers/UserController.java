package com.bootrestemailauth.userapi.controllers;

import java.net.http.HttpHeaders;
import java.sql.Blob;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.bootrestemailauth.userapi.config.MySecurityConfig;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.BlobResponse;
import com.bootrestemailauth.userapi.entities.JwtRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.bootrestemailauth.userapi.entities.UpdatePassword;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//This controller has all crud operations on a user entity

@RestController
public class UserController {

    @Autowired
    public UserDao userDao;

    @Autowired
    public JwtRequest jwtRequest;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public BlobResponse blobResponse;

    @Autowired
    public UpdatePassword updatePassword;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    
    @GetMapping("/users")
    public ResponseEntity<List<JwtRequest>> getUsers(){

        try {
            List<JwtRequest> list = (List<JwtRequest>)userDao.findAll();
            if(list==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/users/{useremail}")
    public ResponseEntity<?> getUser(@PathVariable("useremail") String useremail){
        

        try {

            jwtRequest = userDao.getJwtRequestByuseremail(useremail);
            if(jwtRequest==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(jwtRequest);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    //New function try this image return karne ke liye hua toh god
    @GetMapping("/profile-image")
    public ResponseEntity<?> getProfileImage(  @RequestHeader("Authorization") String authorization){

        try{
            
            String jwtToken = authorization.substring(7);
            String email = jwtUtil.extractUsername(jwtToken);
            jwtRequest = userDao.getJwtRequestByuseremail(email);
            // int blobLength = (int) jwtRequest.getImg().length();
            // byte[] blobAsBytes = jwtRequest.getImg().getBytes(1, blobLength);
            // String s = new String(blobAsBytes);
            blobResponse.setMessage("Successfull");
            blobResponse.setProfile_image(jwtRequest.getImgUrl());
            // System.out.println(s);
            return ResponseEntity.ok(blobResponse);
            
        } catch (Exception e) {
            e.printStackTrace();
            blobResponse.setMessage(e.getMessage());
            blobResponse.setProfile_image("");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(blobResponse);
        }
        
    }

    //Write code to update userDetails i.e. 1. Profile pic   2.User name 
    //Write code to forgotPassword

    @PutMapping("/users/{new_password}") //for updating purpose
    public ResponseEntity<UpdatePassword> forgotPassword(@PathVariable("new_password") String new_password, @RequestHeader("Authorization") String authorization){
        /*
            Step 1: Extract that username wala person from database
            Step 2: set new password and save in db.
        */
        try {
            
            String jwtToken = authorization.substring(7); //Get token which is present after Bearer_ 
            String useremail = jwtUtil.extractUsername(jwtToken); //extract user email from jwtToken
            jwtRequest = userDao.getJwtRequestByuseremail(useremail); //extract that useremail wala person from db
        
            
            String encodedPass = mySecurityConfig.passwordEncoder().encode(new_password); //encode new password B-Crypt encoding algo
           
            jwtRequest.setPassword(encodedPass); //password updated

            userDao.save(jwtRequest);  //save in db
            updatePassword.setMessage("Password Changed Successfully!!");
            return ResponseEntity.ok(updatePassword);
        } catch (Exception e) {
            updatePassword.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatePassword);
        }
    }

    //Baaki codes like update profile images n all ya update name vagere baad mein add kardege...
    // OKAY!!!

}