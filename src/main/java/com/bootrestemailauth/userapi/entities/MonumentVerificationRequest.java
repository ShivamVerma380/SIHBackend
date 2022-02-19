package com.bootrestemailauth.userapi.entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Lob
    @JsonBackReference  // If ye nai lagaya toh blob image jackson exception throw karra n recursion hora
    @Column(name = "monument_image",nullable = false)
    private Blob monument_image;

    @Column(name="monument_location",nullable = false)
    private String monument_location;

    @Column(name = "admin_phone",nullable = false,unique = true)
    private String adminPhoneNo;

    @Lob
    @JsonBackReference  // If ye nai lagaya toh blob image jackson exception throw karra n recursion hora
    @Column(name = "monument_poa",nullable = false)
    private Blob power_of_attorney;

    @Column(name = "admin_aadhar",nullable = false,unique = true)
    private String admin_aadhar;

    @Column(name = "update_status")
    private String update_status;

    public MonumentVerificationRequest() {
    }

    public MonumentVerificationRequest(int adminId, String monumentName, String websiteLink, Blob monument_image,
            String monument_location, String adminPhoneNo, Blob power_of_attorney, String admin_aadhar,
            String update_status) {
        this.adminId = adminId;
        this.monumentName = monumentName;
        this.websiteLink = websiteLink;
        this.monument_image = monument_image;
        this.monument_location = monument_location;
        this.adminPhoneNo = adminPhoneNo;
        this.power_of_attorney = power_of_attorney;
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

    public Blob getMonument_image() {
        return monument_image;
    }

    public void setMonument_image(Blob monument_image) {
        this.monument_image = monument_image;
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

    public Blob getPower_of_attorney() {
        return power_of_attorney;
    }

    public void setPower_of_attorney(Blob power_of_attorney) {
        this.power_of_attorney = power_of_attorney;
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
                + admin_aadhar + ", monumentName=" + monumentName + ", monument_image=" + monument_image
                + ", monument_location=" + monument_location + ", power_of_attorney=" + power_of_attorney
                + ", update_status=" + update_status + ", websiteLink=" + websiteLink + "]";
    }

    

    

}
