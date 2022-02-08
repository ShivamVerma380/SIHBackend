package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class UpdatePassword {
    
    public String message;

    public UpdatePassword() {
    }

    public UpdatePassword(String message) {
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
        return "UpdatePassword [message=" + message + "]";
    }

    

}
