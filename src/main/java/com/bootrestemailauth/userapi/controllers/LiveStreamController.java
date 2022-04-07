package com.bootrestemailauth.userapi.controllers;

import com.bootrestemailauth.userapi.dao.LiveStreamDao;
import com.bootrestemailauth.userapi.entities.LiveStreamDetails;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.nimbusds.oauth2.sdk.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveStreamController {
    
    @Autowired
    public LiveStreamDetails liveStreamDetails;

    @Autowired
    public LiveStreamDao liveStreamDao;

    @Autowired
    public ResponseMessage responseMessage;

    @PostMapping("/add-bambuser-account")
    public ResponseEntity<?> addBambUserAccount(@RequestHeader("Authorization") String Auth,@RequestParam("MonumentName") String monument_name,@RequestParam("application_id") String application_id,@RequestParam("PlayerLink") String playerLink){
        try{
            liveStreamDetails.setApplication_id(application_id);
            liveStreamDetails.setMonument_name(monument_name);
            liveStreamDetails.setResource(playerLink);
            liveStreamDao.save(liveStreamDetails);
            responseMessage.setMessage("Bambuser account added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    // @PostMapping("/get-livestream")
    // public ResponseEntity<?> getLiveStream(@RequestParam("MonumentName") String monument_name){
    //     try{

    //     }catch(Exception e){
    //         e.printStackTrace();
    //         responseMessage.setMessage(e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    //     }
    // }
}
