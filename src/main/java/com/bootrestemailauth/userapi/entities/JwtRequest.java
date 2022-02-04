package com.bootrestemailauth.userapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

//User class

@Entity
@Component
@Table(name="users")
public class JwtRequest {
    
    @Id   //represents primary ke
    @GeneratedValue(strategy = GenerationType.AUTO) //auto generate id
    @Column(name="user_id")
    private int id;

    @Column(name="user_email",unique = true,nullable = false)
    private String useremail;           //use this as username in spring security

    @Column(name="user_name")
    private String name;

    @Column(name="user_password")
    private String password;      //use this as password in spring security

    @Column(name="user_profile")
    private String imgUrl;


    public JwtRequest(){

    }


    public JwtRequest(int id, String useremail, String name, String password, String imgUrl) {
        this.id = id;
        this.useremail = useremail;
        this.name = name;
        this.password = password;
        this.imgUrl = imgUrl;
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


    public String getImgUrl() {
        return imgUrl;
    }


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    @Override
    public String toString() {
        return "JwtRequest [id=" + id + ", imgUrl=" + imgUrl + ", name=" + name + ", password=" + password
                + ", useremail=" + useremail + "]";
    }


    
    

}
