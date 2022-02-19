package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class MonumentVerificationResponse {
    
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MonumentVerificationResponse() {
    }

    public MonumentVerificationResponse(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonumentVerificationResponse [status=" + status + "]";
    }

    
}
