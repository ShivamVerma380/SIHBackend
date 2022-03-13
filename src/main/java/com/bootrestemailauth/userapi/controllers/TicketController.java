package com.bootrestemailauth.userapi.controllers;

import java.sql.Date;

import com.bootrestemailauth.userapi.services.TicketInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TicketController {
    
    @Autowired
    public TicketInfoService ticketInfoService;

    @PostMapping("/add-ticket")
    public ResponseEntity<?> addTicketInfo(@RequestHeader("Authorization") String authorization, @RequestParam("monument_name") String monument_name, @RequestParam("date_of_visit") String visit_date, @RequestParam("verification_id") String verificationId, @RequestParam("gender") String gender, @RequestParam("age") String age, @RequestParam("nationality") String nationality){

        return ticketInfoService.addTicket(authorization, monument_name, visit_date, verificationId, gender, age, nationality);

    }

}
