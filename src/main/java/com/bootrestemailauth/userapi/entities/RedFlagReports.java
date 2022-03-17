package com.bootrestemailauth.userapi.entities;

import java.sql.Blob;
import java.sql.Date;

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
@Table(name="red_flag_reports")
@Component
public class RedFlagReports {
    //admin can do red flag report if record of user is not here on the given day of visit.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="red_flag_id")
    private int reportId;

    @Column(name = "user_email")
    private String userEmail;

    @Lob
    @Column(name = "qr_scan_screenshot")
    @JsonBackReference
    private Blob QrScanScreenshot;

    @Lob
    @Column(name="users_photo")
    @JsonBackReference
    private Blob UsersPhoto;

    @Lob
    @Column(name = "verification_id_photo")
    @JsonBackReference
    private Blob verificationId;

    @Column(name="date_of_visit")
    private Date date_of_visit;

    @Column(name = "monument_name")
    private String monument_name;

    public RedFlagReports() {
    }

    public RedFlagReports(int reportId, String userEmail, Blob qrScanScreenshot, Blob usersPhoto, Blob verificationId,
            Date date_of_visit, String monument_name) {
        this.reportId = reportId;
        this.userEmail = userEmail;
        QrScanScreenshot = qrScanScreenshot;
        UsersPhoto = usersPhoto;
        this.verificationId = verificationId;
        this.date_of_visit = date_of_visit;
        this.monument_name = monument_name;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Blob getQrScanScreenshot() {
        return QrScanScreenshot;
    }

    public void setQrScanScreenshot(Blob qrScanScreenshot) {
        QrScanScreenshot = qrScanScreenshot;
    }

    public Blob getUsersPhoto() {
        return UsersPhoto;
    }

    public void setUsersPhoto(Blob usersPhoto) {
        UsersPhoto = usersPhoto;
    }

    public Blob getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(Blob verificationId) {
        this.verificationId = verificationId;
    }

    public Date getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(Date date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    public String getMonument_name() {
        return monument_name;
    }

    public void setMonument_name(String monument_name) {
        this.monument_name = monument_name;
    }

    @Override
    public String toString() {
        return "RedFlagReports [QrScanScreenshot=" + QrScanScreenshot + ", UsersPhoto=" + UsersPhoto
                + ", date_of_visit=" + date_of_visit + ", monument_name=" + monument_name + ", reportId=" + reportId
                + ", userEmail=" + userEmail + ", verificationId=" + verificationId + "]";
    }

    

    



   

    
    
    

}
