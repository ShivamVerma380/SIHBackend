package com.bootrestemailauth.userapi.services;

import java.sql.Date;

import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.TicketQRDetailsDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.TicketQrRequest;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.helper.QRUploadHelper;
import com.bootrestemailauth.userapi.helper.QRUploadHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.NotFoundException;  
import com.google.zxing.WriterException;  
import com.google.zxing.client.j2se.MatrixToImageWriter;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;  

@Component
public class TicketQRService {
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public UserDao userDao;

    @Autowired
    public MonumentRequest monumentRequest;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public TicketQrRequest ticketRequest;

    @Autowired
    public TicketQRDetailsDao ticketQRDetailsDao;

    @Autowired
    public QRUploadHelper qRUploadHelper;
    public ResponseEntity<?> addTicketQR(String authorization, String monument_name,int no_of_tickets, double fare, int indian_adult, int indian_child, int foreign_adult, int foreign_child, int males, int females, Date date_of_visit){
        try{

            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);
            userRequest = userDao.getUserRequestByuseremail(registered_email);

            if(userRequest == null){
                responseMessage.setMessage("User does not exist with this token");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }

            String msg = "Monument Name: "+monument_name;
            if(indian_adult != 0) msg += "\nNumber of Indian Adults: "+indian_adult;
            if(foreign_adult != 0) msg += "\nNumber of Foreign Adults: "+foreign_adult;
            if(indian_child != 0) msg += "\nNumber of Indian child: "+indian_child;
            if(foreign_child != 0) msg += "\nNumber of Foreign Adults: "+foreign_child;
            if(males != 0) msg += "\nNumber of Male gender count: "+males;
            if(females != 0) msg += "\nNumber of Female gender count: "+females;
            msg += "\nNumber of Tickets: "+no_of_tickets+"\nCalculated Fare: "+fare+"\nDate of Visit: "+date_of_visit+"\nBILL AMOUNT: PAID ONLINE";
            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);
            int monumentID=monumentRequest.getMonumentId();
            int userID=userRequest.getId();
            if(qRUploadHelper.isQRUploaded(msg,monumentID,userID,date_of_visit)){
                
                ticketRequest.setDate_of_visit(date_of_visit);
                ticketRequest.setFare(fare);
                ticketRequest.setMonument_id(monumentID);
                ticketRequest.setNoOfTickets(no_of_tickets);
                ticketRequest.setScanned(false);
                ticketRequest.setUser_id(userID);

                ticketQRDetailsDao.save(ticketRequest);
                responseMessage.setMessage("QR generated successfully!!");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            else{
                responseMessage.setMessage("QR code not generated");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }

            


        }
        catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}