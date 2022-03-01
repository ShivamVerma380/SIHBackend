package com.bootrestemailauth.userapi.entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="Admin_Registration")
public class AdminRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="admin_id")
    private int id;

    @Column(name = "admin_name")
    private String name;

    @Column(name = "admin_email",unique = true,nullable = false)
    private String email;  // username for spring security


    @Column(name="admin_password")
    private String password;

    @Column(name="admin_aadhar")
    private String aadhar_number;

    @Lob
    @Column(name = "admin_image")
    @JsonBackReference
    private Blob img;

    @Column(name = "admin_phone")
    private String phoneNos;

    public AdminRequest() {
    }

    public AdminRequest(int id, String name, String email, String password, String aadhar_number, Blob img,
            String phoneNos) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.aadhar_number = aadhar_number;
        this.img = img;
        this.phoneNos = phoneNos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAadhar_number() {
        return aadhar_number;
    }

    public void setAadhar_number(String aadhar_number) {
        this.aadhar_number = aadhar_number;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public String getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(String phoneNos) {
        this.phoneNos = phoneNos;
    }

    @Override
    public String toString() {
        return "Admin [aadhar_number=" + aadhar_number + ", email=" + email + ", id=" + id + ", img=" + img
                + ", name=" + name + ", password=" + password + ", phoneNos=" + phoneNos + "]";
    }
    

    
}
