package com.bootrestemailauth.userapi.services;

import java.util.ArrayList;

import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAdminDetailsService implements UserDetailsService {

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public AdminRequest adminRequest;

    @Autowired
    public AdminDao adminDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        adminRequest = null;
        try {
            
            adminRequest = adminDao.getAdminRequestByemail(username);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(adminRequest!=null){
            return new User(adminRequest.getEmail(), adminRequest.getPassword(), new ArrayList<>());
        }else{
            try {
                throw new UsernameNotFoundException("User not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public ResponseEntity<?> getAdminByToken(String token){

        try {
           String admin_email = jwtUtil.extractUsername(token); // getting email of admin
           UserDetails userDetails = loadUserByUsername(admin_email);
           return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }        


    }
    
}