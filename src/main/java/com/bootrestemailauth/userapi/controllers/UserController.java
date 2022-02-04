package com.bootrestemailauth.userapi.controllers;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.JwtRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//This controller has all crud operations on a user entity

@RestController
public class UserController {

    @Autowired
    public UserDao userDao;

    @Autowired
    public JwtRequest jwtRequest;
    
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

    

}
