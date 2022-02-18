package com.bootrestemailauth.userapi.controllers;

import com.bootrestemailauth.userapi.dao.AdminAccDetailsDao;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.entities.AdminAccountDetails;
import com.bootrestemailauth.userapi.entities.AdminAccountDetailsResponse;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public AdminAccountDetailsResponse adminAccountDetailsResponse;

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

}
