package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class AdminAccountDetailsResponse {
    
    private String message;

    public AdminAccountDetailsResponse() {
    }

    public AdminAccountDetailsResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminAccountDetailsResponse [message=" + message + "]";
    }
    
}
