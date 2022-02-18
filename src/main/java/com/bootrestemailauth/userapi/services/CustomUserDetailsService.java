package com.bootrestemailauth.userapi.services;

import java.util.ArrayList;

import com.bootrestemailauth.userapi.dao.UserDao;
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
    public UserRequest user;

    @Autowired
    public JwtUtil jwtUiUtil;

    



    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
       //username means email 
        user = null;

        try {
           // user = userDao.getUserByEmail(email);
            user = userDao.getUserRequestByuseremail(useremail); // get_ClassName_By_variablename
            
        } catch (Exception e) {
            e.printStackTrace();

        }
        if(user!=null){
            return new User(user.getUseremail(), user.getPassword(), new ArrayList<>());
        }else{
            try {
                throw new UsernameNotFoundException("User not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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