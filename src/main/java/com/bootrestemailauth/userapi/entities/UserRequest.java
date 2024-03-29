package com.bootrestemailauth.userapi.entities;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Lob;

import org.springframework.stereotype.Component;

//User class 
//this class variables will be stored in database

@Entity
@Component
@Table(name="user_registration")
public class UserRequest {
    
    @Id   //represents primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //auto generate id
    @Column(name="user_id")
    private int id;

    @Column(name="user_email",unique = true,nullable = false)
    private String useremail;           //use this as username in spring security

    @Column(name="user_name")
    private String name;

    @Column(name="user_password")
    private String password;      //use this as password in spring security


    @Lob
    @Column(name="user_image")
    @JsonBackReference  // If ye nai lagaya toh blob image jackson exception throw karra n recursion hora
    private Blob img;

    @Column(name = "red_flag_count")
    private int red_flag_count;

    public UserRequest() {
    }

    public UserRequest(int id, String useremail, String name, String password, Blob img) {
        this.id = id;
        this.useremail = useremail;
        this.name = name;
        this.password = password;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public int getRed_flag_count() {
        return red_flag_count;
    }

    public void setRed_flag_count(int red_flag_count) {
        this.red_flag_count = red_flag_count;
    }

    @Override
    public String toString() {
        return "UserRequest [id=" + id + ", img=" + img + ", name=" + name + ", password=" + password
                + ", red_flag_count=" + red_flag_count + ", useremail=" + useremail + "]";
    }

    

    

    

}
