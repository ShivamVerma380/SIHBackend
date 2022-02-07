package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class BlobResponse {
    private String message;
    private String profile_image;
    public BlobResponse() {
    }
    public BlobResponse(String message, String profile_image) {
        this.message = message;
        this.profile_image = profile_image;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getProfile_image() {
        return profile_image;
    }
    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
    @Override
    public String toString() {
        return "BlobResponse [message=" + message + ", profile_image=" + profile_image + "]";
    }

    
}
