package com.bootrestemailauth.userapi.services;

import java.sql.Date;

import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.TicketDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.TicketRequest;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketInfoService{

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public UserDao userDao;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public MonumentRequest monumentRequest;

    @Autowired
    public TicketRequest ticketRequest;


    @Autowired
    public TicketDao ticketDao;
    
    public ResponseEntity<?> addTicket(String authorization, String monument_name, String visit_date, String verificationId,String gender, String age,String nationality){
        try{

            String jwtToken = authorization.substring(7);
            String registerd_user_email = jwtUtil.extractUsername(jwtToken);

            userRequest = userDao.getUserRequestByuseremail(registerd_user_email);

            if(userRequest == null){
                responseMessage.setMessage("User not Registered");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
            }

            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);

            int monumentID = monumentRequest.getMonumentId();
            int userID = userRequest.getId();
            Date date=Date.valueOf(visit_date);//converting string into sql date.
            TicketRequest newticket = new TicketRequest(monumentID, userID, date, verificationId ,gender,age, nationality);
            
            // ticketRequest.setMonument_id(monumentID);
            // ticketRequest.setUser_id(userID);
            // ticketRequest.setAge(age);
            // ticketRequest.setGender(gender);
            // ticketRequest.setNationality(nationality);
            // ticketRequest.setVerification_id(verificationId);
            // ticketRequest.setVisit_date(visit_date);

            ticketDao.save(newticket);
            responseMessage.setMessage("Ticket added successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        }catch(Exception e){
            e.getStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
        }
    }

    
}
