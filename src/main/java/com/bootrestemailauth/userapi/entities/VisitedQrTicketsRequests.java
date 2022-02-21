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
@Table(name="visited_ticket")
public class VisitedQrTicketsRequests {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name="monument_id")
    private int monument_id;

    @Column(name = "fare")
    private double fare;

    @Column(name = "no_of_tickets")
    private int noOfTickets; 

    @Column(name="date_of_visit")
    private Date date_of_visit;

    public VisitedQrTicketsRequests() {
    }

    public VisitedQrTicketsRequests(int id, int user_id, int monument_id, double fare, int noOfTickets,
            Date date_of_visit) {
        this.id = id;
        this.user_id = user_id;
        this.monument_id = monument_id;
        this.fare = fare;
        this.noOfTickets = noOfTickets;
        this.date_of_visit = date_of_visit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getMonument_id() {
        return monument_id;
    }

    public void setMonument_id(int monument_id) {
        this.monument_id = monument_id;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public Date getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(Date date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    @Override
    public String toString() {
        return "VisitedQrTicketsRequests [date_of_visit=" + date_of_visit + ", fare=" + fare + ", id=" + id
                + ", monument_id=" + monument_id + ", noOfTickets=" + noOfTickets + ", user_id=" + user_id + "]";
    }

    
}
