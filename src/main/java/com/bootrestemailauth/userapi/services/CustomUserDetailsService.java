package com.bootrestemailauth.userapi.services;

import java.util.ArrayList;

import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//CustomUserDetailsService has to override loadByUsername function provided by spring security...

@Component
public class CustomUserDetailsService implements UserDetailsService{


    @Autowired
    public UserDao userDao;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public JwtUtil jwtUiUtil;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public AdminRequest adminRequest;

    public UserRequest findByUsername(String email){
        return userDao.getUserRequestByuseremail(email);
    }

    public AdminRequest findByAdminname(String email){
        return adminDao.getAdminRequestByemail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
       //username means email 
        userRequest = findByUsername(useremail);
        if(userRequest != null){

            return new User(userRequest.getUseremail(), userRequest.getPassword(), new ArrayList<>()); 
        }

        adminRequest = findByAdminname(useremail);
        if(adminRequest != null){

            return new User(adminRequest.getEmail(), adminRequest.getPassword(), new ArrayList<>()); 
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public ResponseEntity<?> getUserByToken(String token){
        try {
            String email = jwtUiUtil.extractUsername(token);
            UserDetails uDetails = loadUserByUsername(email);  //username == email id
            return ResponseEntity.ok(uDetails); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}