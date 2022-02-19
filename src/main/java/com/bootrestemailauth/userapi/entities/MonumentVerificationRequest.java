package com.bootrestemailauth.userapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "monument_verification")
public class MonumentVerificationRequest {
    
    @Id
    @Column(name="admin_id")
    private int adminId;

    @Column(name = "monument_name",nullable = false)
    private String monumentName;

    @Column(name = "website",unique=true,nullable = false)
    private String websiteLink;

    @Column(name = "monument_image_url",nullable = false)
    private String monument_image_url;

    @Column(name="monument_location",nullable = false)
    private String monument_location;

    @Column(name = "admin_phone",nullable = false,unique = true)
    private String adminPhoneNo;

    @Column(name = "monument_poa",nullable = false,unique = true)
    private String power_of_attorney_url;

    @Column(name = "admin_aadhar",nullable = false,unique = true)
    private String admin_aadhar;

    @Column(name = "update_status")
    private String update_status;

    public MonumentVerificationRequest() {
    }

    public MonumentVerificationRequest(int adminId, String monumentName, String websiteLink, String monument_image_url,
            String monument_location, String adminPhoneNo, String power_of_attorney_url, String admin_aadhar,
            String update_status) {
        this.adminId = adminId;
        this.monumentName = monumentName;
        this.websiteLink = websiteLink;
        this.monument_image_url = monument_image_url;
        this.monument_location = monument_location;
        this.adminPhoneNo = adminPhoneNo;
        this.power_of_attorney_url = power_of_attorney_url;
        this.admin_aadhar = admin_aadhar;
        this.update_status = update_status;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getMonumentName() {
        return monumentName;
    }

    public void setMonumentName(String monumentName) {
        this.monumentName = monumentName;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getMonument_image_url() {
        return monument_image_url;
    }

    public void setMonument_image_url(String monument_image_url) {
        this.monument_image_url = monument_image_url;
    }

    public String getMonument_location() {
        return monument_location;
    }

    public void setMonument_location(String monument_location) {
        this.monument_location = monument_location;
    }

    public String getAdminPhoneNo() {
        return adminPhoneNo;
    }

    public void setAdminPhoneNo(String adminPhoneNo) {
        this.adminPhoneNo = adminPhoneNo;
    }

    public String getPower_of_attorney_url() {
        return power_of_attorney_url;
    }

    public void setPower_of_attorney_url(String power_of_attorney_url) {
        this.power_of_attorney_url = power_of_attorney_url;
    }

    public String getAdmin_aadhar() {
        return admin_aadhar;
    }

    public void setAdmin_aadhar(String admin_aadhar) {
        this.admin_aadhar = admin_aadhar;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    @Override
    public String toString() {
        return "MonumentVerificationRequest [adminId=" + adminId + ", adminPhoneNo=" + adminPhoneNo + ", admin_aadhar="
                + admin_aadhar + ", monumentName=" + monumentName + ", monument_image_url=" + monument_image_url
                + ", monument_location=" + monument_location + ", power_of_attorney_url=" + power_of_attorney_url
                + ", update_status=" + update_status + ", websiteLink=" + websiteLink + "]";
    }

    

}
