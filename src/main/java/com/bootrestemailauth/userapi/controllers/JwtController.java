package com.bootrestemailauth.userapi.controllers;

import com.bootrestemailauth.userapi.config.MySecurityConfig;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.JwtRequest;
import com.bootrestemailauth.userapi.entities.JwtResponse;
import com.bootrestemailauth.userapi.helper.FileUploadHelper;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class JwtController {
    
    @Autowired
    public JwtRequest jwtRequest;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    @Autowired
    public JwtResponse jwtResponse;

    @Autowired
    public FileUploadHelper fileUploadHelper;

    @Autowired
    public UserDao userDao;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("welcome to private club");
    }


    

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("profile-image") MultipartFile file){
        String imgUrl = null;
        try {
            /*if(file.isEmpty()){

                jwtResponse.setMessage("File is Empty!!");
                jwtResponse.setToken(null);
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
            }*/
            if(fileUploadHelper.isFileUploaded(file)){
                imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
            }
            String encodedPassword = mySecurityConfig.passwordEncoder().encode(password);
            
            jwtRequest.setId(12);
            jwtRequest.setUseremail(email);
            jwtRequest.setPassword(encodedPassword);
            jwtRequest.setName(name);
            jwtRequest.setImgUrl(imgUrl);

            JwtRequest existingUser = userDao.getJwtRequestByuseremail(email);
            if(existingUser!=null){
                jwtResponse.setMessage("User already exists!!");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
            }
            
            userDao.save(jwtRequest);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (Exception e) {
            e.printStackTrace();
            jwtResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jwtResponse);
        }
        //Now user is authenticated

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUseremail());
        
        String token = jwtUtil.generateToken(userDetails);
        
        jwtResponse.setMessage("User Registered Successfully");
        jwtResponse.setToken(token);

        return ResponseEntity.ok(jwtResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String aurhorization,@RequestParam("email") String email,@RequestParam("password") String password){
        try {
            
            String jwtToken = aurhorization.substring(7);
            String email_registered = jwtUtil.extractUsername(jwtToken);
            
            JwtRequest userDetails = userDao.getJwtRequestByuseremail(email_registered);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            

            if(email.equals(userDetails.getUseremail()) && bCryptPasswordEncoder.matches(password,userDetails.getPassword())){
                jwtResponse.setMessage("User logged in successfully");
                jwtResponse.setToken(jwtToken);
                return ResponseEntity.ok(jwtResponse);

            }else{
                jwtResponse.setMessage("Bad Credentials");
                jwtResponse.setToken(null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtResponse);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        
    }

    

}
