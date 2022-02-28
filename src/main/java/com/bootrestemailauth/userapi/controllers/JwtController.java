package com.bootrestemailauth.userapi.controllers;

import java.io.FileInputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

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
import com.bootrestemailauth.userapi.services.PasswordService;

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

import io.jsonwebtoken.impl.DefaultClaims;

import org.hibernate.*;

@RestController
public class JwtController {
    
    @Autowired
    public UserRequest userRequest;

    @Autowired
    public UserRequest userRequest1;

    @Autowired
    public AdminRequest admin;

    @Autowired
    public AdminRequest admin1;

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

    @Autowired
    public PasswordService passwordService;

    

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
                userRequest1 = userDao.getUserRequestByuseremail(email);
                if(userRequest1!=null){
                    jwtResponse.setToken(null);
                    jwtResponse.setMessage("This email id is already registered with user role");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }
                String uploadDir = fileUploadHelper.isFileUploaded(file, email, role);
                if(uploadDir!=null){
    
                    String ext = file.getOriginalFilename();
                    int i=0;
                    for(;i<ext.length();i++){
                        if(ext.charAt(i)=='.') break;
                    }
    
                    ext = ext.substring(i+1);
    
                    imgUrl = uploadDir+email+"."+ext;
                    System.out.println(imgUrl);
                }

                String encodedPassword = mySecurityConfig.passwordEncoder().encode(password);
                
                admin.setEmail(email);
                admin.setName(name);
                admin.setPassword(encodedPassword);
                admin.setImg_url(imgUrl);
                admin.setAadhar_number(null);
                admin.setPhoneNos(null);
                System.out.println(admin.toString());

                AdminRequest existingAdmin = adminDao.getAdminRequestByemail(email);

                if(existingAdmin!=null){
                    jwtResponse.setToken("null");
                    jwtResponse.setMessage("Admin already exists");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }

                adminDao.save(admin);
                System.out.println("***********");
                
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

                admin1 = adminDao.getAdminRequestByemail(email);

                if(admin1!=null){
                    jwtResponse.setToken(null);
                    jwtResponse.setMessage("This email id is already registered with admin role");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }

                
                String uploadDir = fileUploadHelper.isFileUploaded(file, email, role);
                if(uploadDir!=null){
    
                    String ext = file.getOriginalFilename();
                    int i=0;
                    for(;i<ext.length();i++){
                        if(ext.charAt(i)=='.') break;
                    }
    
                    ext = ext.substring(i+1);
    
                    imgUrl = uploadDir+email+"."+ext;
                }
    
                Session session = entityManager.unwrap(Session.class);
    
                Blob imageData = session.getLobHelper().createBlob(file.getInputStream(),file.getSize());
                System.out.println(imageData);
                
                
    
                String encodedPassword = mySecurityConfig.passwordEncoder().encode(password); // bcrypt encoded password
                
                // userRequest.setId(12);
                userRequest.setUseremail(email);
                userRequest.setPassword(encodedPassword);
                userRequest.setName(name);
                userRequest.setImg(imageData);
                userRequest.setImgUrl(imgUrl);
    
                UserRequest existingUser = userDao.getUserRequestByuseremail(email); // userDao.get_ClassName_By_variablename
                if(existingUser!=null){
                    jwtResponse.setMessage("User already exists!!");
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(jwtResponse);
                }
                
                userDao.save(userRequest);
    
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)); //spring security
    
            } catch (Exception e) {
                e.printStackTrace();
                jwtResponse.setMessage(e.getMessage());
                jwtResponse.setToken(null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jwtResponse);
            }
            //Now user is authenticated
    
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userRequest.getUseremail());
            
            String token = jwtUtil.generateToken(userDetails);
            
            jwtResponse.setMessage("User Registered Successfully");
            jwtResponse.setToken(token);
    
            return ResponseEntity.ok(jwtResponse);
        }

        

    }

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorization,@RequestParam("email") String email,@RequestParam("password") String password, @RequestParam("role") String role){
        try {
            
            String jwtToken = authorization.substring(7);
            String email_registered = jwtUtil.extractUsername(jwtToken);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(role.equalsIgnoreCase("user")){
                UserRequest userDetails = userDao.getUserRequestByuseremail(email_registered);
                if(userDetails != null){
                    if(email.equals(userDetails.getUseremail()) && bCryptPasswordEncoder.matches(password,userDetails.getPassword())){
                        jwtResponse.setMessage("User logged in successfully");
                        jwtResponse.setToken(jwtToken);
                        return ResponseEntity.ok(jwtResponse);
        
                    }else{
                        jwtResponse.setMessage("Bad Credentials");
                        jwtResponse.setToken(null);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtResponse);
                    }
                }
            }
            if(role.equalsIgnoreCase("admin")){
                AdminRequest adminDetails = adminDao.getAdminRequestByemail(email_registered);
                if(adminDetails!=null){
                    if(email.equals(adminDetails.getEmail()) && bCryptPasswordEncoder.matches(password,adminDetails.getPassword())){
                        jwtResponse.setMessage("Admin logged in successfully");
                        jwtResponse.setToken(jwtToken);
                        return ResponseEntity.ok(jwtResponse);
        
                    }else{
                        jwtResponse.setMessage("Bad Credentials");
                        jwtResponse.setToken(null);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(jwtResponse);
                    }
                }
            }      


        } catch (Exception e) {
            e.printStackTrace();
            jwtResponse.setToken(null);
            jwtResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jwtResponse);
        }
        jwtResponse.setMessage("Not found");
        jwtResponse.setToken(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jwtResponse);
        
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception{
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String,Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);

        String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString()); 

        jwtResponse.setMessage("Token refreshed successfully!!");
        jwtResponse.setToken(token);
        return ResponseEntity.ok(jwtResponse);

    }

    @PostMapping("/forgot-password")
    public  ResponseEntity<?> forgotPassword(@RequestHeader("Authorization") String authorization){
        return passwordService.forgotPassword(authorization);
    }

    @PostMapping("/update-password")
    public  ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String authorization,@RequestParam("password") String password){
        return passwordService.updatePassword(authorization, password);
    }

    private Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
    }

    

}