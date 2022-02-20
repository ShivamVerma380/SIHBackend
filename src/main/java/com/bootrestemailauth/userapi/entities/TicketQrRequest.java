package com.bootrestemailauth.userapi.entities;
import java.sql.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "Ticket_QR")
public class TicketQrRequest {

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

    @Column(name = "is_scanned")
    private boolean isScanned;

    @Column(name = "qr_code")
    private String qr_code;

    @Column(name="date_of_visit")
    private Date date_of_visit;

    public TicketQrRequest(int id,int user_id, int monument_id, double fare, int noOfTickets, boolean isScanned,
            String qr_code,Date date_of_visit) {
        this.id = id;
        this.user_id = user_id;
        this.monument_id = monument_id;
        this.fare = fare;
        this.noOfTickets = noOfTickets;
        this.isScanned = isScanned;
        this.qr_code = qr_code;
        this.date_of_visit = date_of_visit;
    }

    

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public Date getDate_of_visit() {
        return date_of_visit;
    }



    public void setDate_of_visit(Date date_of_visit) {
        this.date_of_visit = date_of_visit;
    }



    public TicketQrRequest() {
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

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    @Override
    public String toString() {
        return "TicketQrRequest [fare=" + fare + ", isScanned=" + isScanned + ", monument_id=" + monument_id
                + ", noOfTickets=" + noOfTickets + ", qr_code=" + qr_code + ", user_id=" + user_id + "]";
    }

    
}