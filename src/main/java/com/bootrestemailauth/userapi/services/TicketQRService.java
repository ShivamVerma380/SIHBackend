package com.bootrestemailauth.userapi.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.TicketQRDetailsDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.dao.VisitedQrTicketDao;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.TicketQrRequest;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.entities.VisitedQrTicketsRequests;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.helper.QRUploadHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
  

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
    public VisitedQrTicketsRequests visitedQrTicketsRequests;

    @Autowired
    public VisitedQrTicketDao visitedQrTicketDao;

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
    
                String QRUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/QRcode/").path(monumentID+"_"+userID+"_"+date_of_visit+".jpg").toUriString();
                
                ticketRequest.setQr_code(QRUrl);
                ticketRequest.setDate_of_visit(date_of_visit);
                ticketRequest.setFare(fare);
                ticketRequest.setMonument_id(monumentID);
                ticketRequest.setNoOfTickets(no_of_tickets);
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


    public ResponseEntity<?> removeQRticket(String authorization,String monument_name,Date date_of_visit){
        try {
            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);
            userRequest =  userDao.getUserRequestByuseremail(registered_email);
            if(userRequest==null){
                responseMessage.setMessage("User does not exist with this account");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);

            }
            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);
            List<TicketQrRequest> qrList= ticketQRDetailsDao.getTicketQrRequestByuserId(userRequest.getId());


            System.out.println(qrList.toString());
            for(int i=0;i<qrList.size();i++){

                System.out.println("In for loop\n\n");
                int current_monument_id = qrList.get(i).getMonument_id();
                Date current_date = qrList.get(i).getDate_of_visit();
                int req_monId = monumentRequest.getMonumentId();
                System.out.println("Current Monument Id +"+current_monument_id+"\nReq monument Id:"+monumentRequest.getMonumentId());
                System.out.println("Current Date Id +"+current_date+"\nReq Date Id:"+date_of_visit);
                if(current_monument_id==monumentRequest.getMonumentId() && (current_date.compareTo(date_of_visit)==0)){
                    System.out.println("In if lopp\n\n");
                    visitedQrTicketsRequests.setDate_of_visit(date_of_visit);
                    visitedQrTicketsRequests.setFare(qrList.get(i).getFare());
                    visitedQrTicketsRequests.setMonument_id(current_monument_id);
                    visitedQrTicketsRequests.setNoOfTickets(qrList.get(i).getNoOfTickets());
                    visitedQrTicketsRequests.setUser_id(userRequest.getId());
                    //System.out.println();
                    // C:\Users\shiva\SpringBoot-VSCode\SIHBackend\src\main\resources\static\image\QRcode\3_2_1998-03-12.jpg
                    visitedQrTicketDao.save(visitedQrTicketsRequests);
                    ticketQRDetailsDao.delete(qrList.get(i));
                    //String QRUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/QRcode/").path(current_monument_id+"_"+userRequest.getId()+"_"+date_of_visit+".jpg").toUriString();
                   String QRUrl = "C:\\Users\\shiva\\SpringBoot-VSCode\\SIHBackend\\src\\main\\resources\\static\\image\\QRcode\\"+current_monument_id+"_"+userRequest.getId()+"_"+date_of_visit+".jpg";
                    Files.deleteIfExists(Paths.get(QRUrl));
                }

            }

            responseMessage.setMessage("QR ticket removed successfully");
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}