package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {
    
    private String message;

    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
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
        return "Response [message=" + message + "]";
    }
    
}
