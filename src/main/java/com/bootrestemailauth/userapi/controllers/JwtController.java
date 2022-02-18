package com.bootrestemailauth.userapi.controllers;

import java.io.FileInputStream;
import java.sql.Blob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bootrestemailauth.userapi.config.MySecurityConfig;
import com.bootrestemailauth.userapi.dao.AdminDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.entities.AdminRequest;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.entities.JwtResponse;
import com.bootrestemailauth.userapi.entities.UpdatePassword;
import com.bootrestemailauth.userapi.helper.FileUploadHelper;
import com.bootrestemailauth.userapi.helper.JwtUtil;
import com.bootrestemailauth.userapi.helper.LobHelper;
import com.bootrestemailauth.userapi.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.hibernate.*;

@RestController
public class JwtController {
    
    @Autowired
    public UserRequest jwtRequest;

    @Autowired
    public AdminRequest admin;

    @Autowired
    public MySecurityConfig mySecurityConfig;

    @Autowired
    public JwtResponse jwtResponse;


    @Autowired
    public LobHelper lobHelper;

    @PersistenceContext
    public EntityManager entityManager;


    @Autowired
    public UserDao userDao;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public FileUploadHelper fileUploadHelper;

    

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("welcome to private club");
    }


    
    //@Transactional //Without this error is coming
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestParam("role") String role,@RequestParam("email") String email,@RequestParam("name") String name,@RequestParam("password") String password,@RequestParam("profile-image") MultipartFile file){
        String imgUrl=null;
        
        if(role.equalsIgnoreCase("Admin")){
            try {
                System.out.println("In admin");
                if(fileUploadHelper.isFileUploaded(file, email, role)){
    
                    String ext = file.getOriginalFilename();
                    int i=0;
                    for(;i<ext.length();i++){
                        if(ext.charAt(i)=='.') break;
                    }
    
                    ext = ext.substring(i+1);
    
                    imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/admin/").path(email).path(".").path(ext).toUriString();
                }

                String encodedPassword = mySecurityConfig.passwordEncoder().encode(password);
                
                admin.setEmail(email);
                admin.setName(name);
                admin.setPassword(encodedPassword);
                admin.setImg_url(imgUrl);
                admin.setAadhar_number(null);
                admin.setPhoneNos(null);

                AdminRequest existingAdmin = adminDao.getAdminRequestByemail(email);

                if(existingAdmin!=null){
                    jwtResponse.setToken("null");
                    jwtResponse.setMessage("Admin already exists");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }

                adminDao.save(admin);
                
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); //spring security

            } catch (Exception e) {
                //e.printStackTrace();
                jwtResponse.setMessage(e.getMessage());
                return ResponseEntity.ok(jwtResponse);
            }
            //admin has been authenticated successfully
            
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(admin.getEmail()); //username == email
            
            String token = jwtUtil.generateToken(userDetails);

            jwtResponse.setMessage("Admin Registered successfully");
            jwtResponse.setToken(token);

            return ResponseEntity.ok(jwtResponse);
        }else{
            try {
                if(fileUploadHelper.isFileUploaded(file, email, role)){
    
                    String ext = file.getOriginalFilename();
                    int i=0;
                    for(;i<ext.length();i++){
                        if(ext.charAt(i)=='.') break;
                    }
    
                    ext = ext.substring(i+1);
    
                    imgUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/user/").path(email).path(".").path(ext).toUriString();
                }
    
                Session session = entityManager.unwrap(Session.class);
    
                Blob imageData = session.getLobHelper().createBlob(file.getInputStream(),file.getSize());
                System.out.println(imageData);
                
                
    
                String encodedPassword = mySecurityConfig.passwordEncoder().encode(password); // bcrypt encoded password
                
                jwtRequest.setId(12);
                jwtRequest.setUseremail(email);
                jwtRequest.setPassword(encodedPassword);
                jwtRequest.setName(name);
                jwtRequest.setImg(imageData);
                jwtRequest.setImgUrl(imgUrl);
    
                UserRequest existingUser = userDao.getUserRequestByuseremail(email); // userDao.get_ClassName_By_variablename
                if(existingUser!=null){
                    jwtResponse.setMessage("User already exists!!");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }
                
                userDao.save(jwtRequest);
    
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); //spring security
    
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

        

    }

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String aurhorization,@RequestParam("email") String email,@RequestParam("password") String password){
        try {
            
            String jwtToken = aurhorization.substring(7);
            String email_registered = jwtUtil.extractUsername(jwtToken);
            
            UserRequest userDetails = userDao.getUserRequestByuseremail(email_registered);

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