package com.bootrestemailauth.userapi.controllers;

import java.sql.Time;

import com.bootrestemailauth.userapi.dao.AdminAccDetailsDao;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.entities.AdminAccountDetails;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.MonumentVerificationRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.services.MonumentService;
import com.bootrestemailauth.userapi.services.VerifyMonumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AdminController {
    
    @Autowired
    public AdminAccDetailsDao adminAccDetailsDao;

    @Autowired
    public AdminAccountDetails adminAccountDetails;

    @Autowired
    public AdminRequest adminRequest;
    
    @Autowired
    public AdminDao adminDao;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public ResponseMessage adminAccountDetailsResponse;

    @Autowired
    public VerifyMonumentService verifyMonumentService;

    @Autowired
    public MonumentService monumentService;

    @PostMapping("/admin/accountDetails")
    public ResponseEntity<?> addBankDetails(@RequestHeader("Authorization") String authorization,@RequestParam("account_no") String accountNo,@RequestParam("ifsc_code") String ifsc_code,@RequestParam("bank_name") String bank_name,@RequestParam("branch_name") String branch_name,@RequestParam("acc_holder_name") String acc_holder_name){
        
        try {
            String jwtToken = authorization.substring(7);
            String registered_email = jwtUtil.extractUsername(jwtToken);

            //remove admin_id of registered email from adminDao
            adminRequest = adminDao.getAdminRequestByemail(registered_email);

            //Now set all things in  admin account details object

            adminAccountDetails.setAdminId(adminRequest.getId());
            adminAccountDetails.setAccountNo(accountNo);
            adminAccountDetails.setAcc_holder_name(acc_holder_name);
            adminAccountDetails.setBank_name(bank_name);
            adminAccountDetails.setBranch_name(branch_name);
            adminAccountDetails.setIfsc_code(ifsc_code);

            adminAccDetailsDao.save(adminAccountDetails);

            adminAccountDetailsResponse.setMessage("Admin Account details saved successfully!!");

            return ResponseEntity.ok(adminAccountDetailsResponse);
            
            
        } catch (Exception e) {
            
            e.printStackTrace();

            adminAccountDetailsResponse.setMessage(e.getMessage());

            return ResponseEntity.ok(adminAccountDetailsResponse);
        }

    }

    @PostMapping("/admin/verify-monument")
    public ResponseEntity<?> verifyMonument(@RequestHeader("Authorization") String authorization,@RequestParam("monument_name") String monument_name,@RequestParam("website") String website,@RequestParam("monument_image") MultipartFile monumentImage,@RequestParam("monument_location") String monument_location,@RequestParam("admin_phone") String admin_phone,@RequestParam("monument_poa") MultipartFile monument_poa,@RequestParam("admin_aadhar") String admin_aadhar, @RequestParam("monument_type") String monument_type){
        
        //return verifyMonumentService.verifyMonument(authorization, monument_name, website, monumentImage, monument_location, admin_phone, monument_poa, admin_aadhar);
        return verifyMonumentService.verifyMonument(authorization, monument_name, website, monumentImage, monument_location, admin_phone, monument_poa, admin_aadhar,monument_type);   
        //respect for giving auto parameters .. vscode is god

    }

    @PostMapping("/add-monument")  //ye function admin kabhi nai call karega ye apan karege for setting required values in monument table and other values will be updated later by admin.
    public ResponseEntity<?> addMonument(@RequestHeader("Authorization") String authorization,@RequestParam("monument_name") String monument_name,@RequestParam("website") String website,@RequestParam("monument_image") MultipartFile monumentImage,@RequestParam("monument_location") String monument_location,@RequestParam("monument_poa") MultipartFile monument_poa,@RequestParam("monument_type") String monument_type,@RequestParam("admin_aadhar") String admin_aadhar,@RequestParam("admin_phone")String admin_phone){
    
        return monumentService.addMonument(authorization, monument_name, website, monumentImage, monument_location, monument_poa, monument_type, admin_aadhar, admin_phone);
    }

    @PostMapping("/admin/add-monument")
    public ResponseEntity<?> monumentInfo(@RequestHeader("Authorization")String authorization, @RequestParam("monument_name") String monument_name,@RequestParam("monument_preview")MultipartFile video,@RequestParam("opening_time") Time opening_time,@RequestParam("closing_time") Time closing_time ,@RequestParam("description") String description,@RequestParam("indian_adult") double indian_adult,@RequestParam("indian_child") double indian_child,@RequestParam("foreign_adult") double foreign_adult,@RequestParam("foreign_child") double foreign_child,@RequestParam("closed_day") String closed_day){
        return monumentService.addmonumentInfo(authorization, monument_name, video, opening_time, closing_time, description, indian_adult, indian_child, foreign_adult, foreign_child, closed_day);
    }
    
    @GetMapping("/monuments")
    public ResponseEntity<?> getAllMonuments(){
        return monumentService.getAllMonuments();
    }
}
