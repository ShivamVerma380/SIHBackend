package com.bootrestemailauth.userapi.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "Ticket_Info")
public class TicketRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private int id;

    @Column(name = "monument_id")
    private int monument_id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "date_of_visit")
    private Date visit_date;

    @Column(name = "verification_id")
    private String verification_id;

    @Column(name= "gender")
    private String gender;

    @Column(name = "age")
    private String age;

    @Column(name = "nationality")
    private String nationality;


    
    public TicketRequest() {
    }

    

    public TicketRequest(int monument_id, int user_id, Date visit_date, String verification_id, String gender,
            String age, String nationality) {
        this.monument_id = monument_id;
        this.user_id = user_id;
        this.visit_date = visit_date;
        this.verification_id = verification_id;
        this.gender = gender;
        this.age = age;
        this.nationality = nationality;
    }



    public TicketRequest(int id, int monument_id, int user_id, Date visit_date, String verification_id, String gender,
            String age, String nationality) {
        this.id = id;
        this.monument_id = monument_id;
        this.user_id = user_id;
        this.visit_date = visit_date;
        this.verification_id = verification_id;
        this.gender = gender;
        this.age = age;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonument_id() {
        return monument_id;
    }

    public void setMonument_id(int monument_id) {
        this.monument_id = monument_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(Date visit_date) {
        this.visit_date = visit_date;
    }

    public String getVerification_id() {
        return verification_id;
    }

    public void setVerification_id(String verification_id) {
        this.verification_id = verification_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "TicketInfo [age=" + age + ", gender=" + gender + ", id=" + id + ", monument_id=" + monument_id
                + ", nationality=" + nationality + ", user_id=" + user_id + ", verification_id=" + verification_id
                + ", visit_date=" + visit_date + "]";
    }


}
