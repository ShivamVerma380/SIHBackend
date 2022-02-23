package com.bootrestemailauth.userapi.controllers;

import com.bootrestemailauth.userapi.services.MonumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonumentController {


    @Autowired
    public MonumentService monumentService;
    
    @GetMapping("/monuments")
    public ResponseEntity<?> getAllMonuments(){
        return monumentService.getAllMonuments();
    }

    @GetMapping("/monuments/{type}")
    public ResponseEntity<?> getMonumentByType(@PathVariable("type") String type){
        return monumentService.getMonumentsByType(type);
    }
    
}