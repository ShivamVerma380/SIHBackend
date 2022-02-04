package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

//This class returns token in json format
@Component
public class JwtResponse {

    private String message;

    public String token;

    public JwtResponse() {
    }

    public JwtResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JwtResponse [message=" + message + ", token=" + token + "]";
    }

    
    
}
