package com.bootrestemailauth.userapi.services;

import com.bootrestemailauth.userapi.dao.AdminAccDetailsDao;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.RedFlagReportDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.AdminAccountDetails;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.RedFlagReports;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class AdminService {
    
    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public AdminRequest adminRequest;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public AdminAccountDetails adminAccountDetails;

    @Autowired
    public AdminAccDetailsDao accDetailsDao;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public UserDao userDao;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public RedFlagReports redFlagReports;


    @Autowired
    public RedFlagReportDao redFlagReportDao;

    @PersistenceContext
    public EntityManager entityManager;

    public ResponseEntity<?> getMonumentsByadminId(String authorization){

        try {
            String jwtToken = authorization.substring(7);
            String reg_email = jwtUtil.extractUsername(jwtToken);

            adminRequest = adminDao.getAdminRequestByemail(reg_email);

            if(adminRequest==null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // List<AdminRequest> answer= (List<AdminRequest>) adminDao.getAdminRequestByemail(reg_email);
            // if(answer.isEmpty()){
            //     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            // }   
            List<MonumentRequest> answer = monumentDao.getMonumentRequestByadminId(adminRequest.getId());
            if(answer.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        

    }

    public ResponseEntity<?> getAccountDetails(String authorization){
        try {
            String jwtToken = authorization.substring(7);
            String reg_email = jwtUtil.extractUsername(jwtToken);
            adminRequest = adminDao.getAdminRequestByemail(reg_email);
            if(adminRequest==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            adminAccountDetails = accDetailsDao.getAdminAccountDetailsByadminId(adminRequest.getId());
            return ResponseEntity.ok(adminAccountDetails);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<?> reportuser(String authorization,String user_email,String monument_name,String date_of_visit,MultipartFile qrScan,MultipartFile usersPhoto,MultipartFile verificationId){
        try {
            userRequest = userDao.getUserRequestByuseremail(user_email);
            if(userRequest==null){
                responseMessage.setMessage("User Does not exist..Please enter correct user email id");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            List<RedFlagReports> user_reports = (List<RedFlagReports>)redFlagReportDao.getRedFlagReportsByuserEmail(user_email);
            System.out.println(user_reports.toString());
            Date date=Date.valueOf(date_of_visit);
            System.out.println(date);
            System.out.println(monument_name);
            System.out.println(user_email);
            for(int i=0;i<user_reports.size();i++){
                if(user_reports.get(i).getDate_of_visit().toString().equals(date.toString()) && user_reports.get(i).getUserEmail().equalsIgnoreCase(user_email) && user_reports.get(i).getMonument_name().equalsIgnoreCase(monument_name)){
                    responseMessage.setMessage("You have already reported the user..We will investigate into the matter");
                    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
                }
            }
            Session session = entityManager.unwrap(Session.class);
            Blob qrScanBlob = session.getLobHelper().createBlob(qrScan.getInputStream(),qrScan.getSize());
            // monument.setMonumentPOA(monumentPoaData);

            //2a: Id code
             
            //2b: File upload and urls store
            Blob usersPhotoBlob = session.getLobHelper().createBlob(usersPhoto.getInputStream(),usersPhoto.getSize());
            Blob verificationIdBlob = session.getLobHelper().createBlob(verificationId.getInputStream(),verificationId.getSize());
            redFlagReports.setDate_of_visit(date);
            redFlagReports.setMonument_name(monument_name);
            redFlagReports.setQrScanScreenshot(qrScanBlob);
            redFlagReports.setUsersPhoto(usersPhotoBlob);
            redFlagReports.setVerificationId(verificationIdBlob);
            redFlagReports.setUserEmail(user_email);

            redFlagReportDao.save(redFlagReports);


            userRequest.setRed_flag_count(userRequest.getRed_flag_count()+1);
            userDao.save(userRequest);
            responseMessage.setMessage("Thank you for reporting...We will look into this matter");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}

