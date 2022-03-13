package com.bootrestemailauth.userapi.controllers;

import java.sql.Date;

import com.bootrestemailauth.userapi.services.TicketQRService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketQRController {
    
    @Autowired
    public TicketQRService ticketQRService;

    @PostMapping("/addQRticket")
    public ResponseEntity<?> addQRticket(@RequestHeader("Authorization") String authorization, @RequestParam("monument_name") String monument_name, @RequestParam("no_of_tickets") int no_of_tickets, @RequestParam("fare") double fare,@RequestParam("indian_adult")int indian_adult,@RequestParam("indian_child")int indian_child,@RequestParam("foreign_adult")int foreign_adult,@RequestParam("foreign_child")int foreign_child,@RequestParam("males")int males,@RequestParam("females")int females,@RequestParam("date_of_visit")String date_of_visit){
        return ticketQRService.addTicketQR(authorization, monument_name, no_of_tickets, fare, indian_adult, indian_child, foreign_adult, foreign_child, males, females, date_of_visit);
    }

    @DeleteMapping("/removeQRticket")
    public ResponseEntity<?> removeQRticket(@RequestHeader("Authorization") String authorization,@RequestParam("monument_name") String monument_name,@RequestParam("date_of_visit")String date_of_visit){
        
        return ticketQRService.removeQRticket(authorization, monument_name, date_of_visit);
    }
}
