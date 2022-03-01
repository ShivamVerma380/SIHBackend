package com.bootrestemailauth.userapi.entities;

import java.sql.Blob;
import java.sql.Time;

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
@Table(name="monument")
public class MonumentRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="monument_id")
    private int monumentId;

    @Column(name="admin_id",unique = false)
    private int adminId;

    @Lob
    @Column(name="preview")
    @JsonBackReference
    private Blob monumentPreview;

    @Column(name = "start_time")
    private Time openingTime;

    @Column(name = "close_time")
    private Time closingTime;

    @Column(name="name",unique = true)
    private String monumentName;

    @Lob
    @Column(name="image")
    @JsonBackReference
    private Blob monumentImage;

    @Lob
    @Column(name = "poa")
    @JsonBackReference
    private Blob monumentPOA;

    @Column(name = "description")
    private String monumentDescription;

    @Column(name = "indian_adult_fare",precision = 10,scale = 2)
    private double indianAdultFare;

    @Column(name = "indian_child_fare",precision = 10,scale = 2)
    private double indianChildFare;

    @Column(name = "foreign_adult_fare",precision = 10,scale = 2)
    private double foreignAdultFare;

    @Column(name = "foreign_child_fare",precision = 10,scale = 2)
    private double foreignChildFare;

    @Column(name="closed_day")
    private String closedDay;

    @Column(name = "websiteLink",unique = true)
    private String websiteLink;

    @Column(name = "type")
    private String monumentType;

    @Column(name = "location")
    private String monumentLocation;

    public MonumentRequest() {
    }
   

    public MonumentRequest( int adminId, String monumentName, Blob monumentImageUrl, Blob monumentPOA,
            String websiteLink, String monumentType, String monumentLocation) {
        this.adminId = adminId;
        this.monumentName = monumentName;
        this.monumentImage = monumentImage;
        this.monumentPOA = monumentPOA;
        this.websiteLink = websiteLink;
        this.monumentType = monumentType;
        this.monumentLocation = monumentLocation;
    }





    public MonumentRequest(int monumentId, int adminId, Blob monumentPreview, Time openingTime, Time closingTime,
            String monumentName, Blob monumentImageUrl, Blob monumentPOA, String monumentDescription,
            double indianAdultFare, double indianChildFare, double foreignAdultFare, double foreignChildFare,
            String closedDay, String websiteLink, String monumentType, String monumentLocation) {
        this.monumentId = monumentId;
        this.adminId = adminId;
        this.monumentPreview = monumentPreview;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.monumentName = monumentName;
        this.monumentImage = monumentImageUrl;
        this.monumentPOA = monumentPOA;
        this.monumentDescription = monumentDescription;
        this.indianAdultFare = indianAdultFare;
        this.indianChildFare = indianChildFare;
        this.foreignAdultFare = foreignAdultFare;
        this.foreignChildFare = foreignChildFare;
        this.closedDay = closedDay;
        this.websiteLink = websiteLink;
        this.monumentType = monumentType;
        this.monumentLocation = monumentLocation;
    }


    public int getMonumentId() {
        return monumentId;
    }





    public void setMonumentId(int monumentId) {
        this.monumentId = monumentId;
    }





    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Blob getMonumentPreview() {
        return monumentPreview;
    }

    public void setMonumentPreview(Blob monumentPreview) {
        this.monumentPreview = monumentPreview;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public String getMonumentName() {
        return monumentName;
    }

    public void setMonumentName(String monumentName) {
        this.monumentName = monumentName;
    }

    public Blob getMonumentImageUrl() {
        return monumentImage;
    }

    public void setMonumentImageUrl(Blob monumentImageUrl) {
        this.monumentImage = monumentImageUrl;
    }

    public Blob getMonumentPOA() {
        return monumentPOA;
    }

    public void setMonumentPOA(Blob monumentPOA) {
        this.monumentPOA = monumentPOA;
    }

    public String getMonumentDescription() {
        return monumentDescription;
    }

    public void setMonumentDescription(String monumentDescription) {
        this.monumentDescription = monumentDescription;
    }

    public double getIndianAdultFare() {
        return indianAdultFare;
    }

    public void setIndianAdultFare(double indianAdultFare) {
        this.indianAdultFare = indianAdultFare;
    }

    public double getIndianChildFare() {
        return indianChildFare;
    }

    public void setIndianChildFare(double indianChildFare) {
        this.indianChildFare = indianChildFare;
    }

    public double getForeignAdultFare() {
        return foreignAdultFare;
    }

    public void setForeignAdultFare(double foreignAdultFare) {
        this.foreignAdultFare = foreignAdultFare;
    }

    public double getForeignChildFare() {
        return foreignChildFare;
    }

    public void setForeignChildFare(double foreignChildFare) {
        this.foreignChildFare = foreignChildFare;
    }

    public String getClosedDay() {
        return closedDay;
    }

    public void setClosedDay(String closedDay) {
        this.closedDay = closedDay;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getMonumentType() {
        return monumentType;
    }

    public void setMonumentType(String monumentType) {
        this.monumentType = monumentType;
    }



    public String getMonumentLocation() {
        return monumentLocation;
    }



    public void setMonumentLocation(String monumentLocation) {
        this.monumentLocation = monumentLocation;
    }



    @Override
    public String toString() {
        return "Monument [adminId=" + adminId + ", closedDay=" + closedDay + ", closingTime=" + closingTime
                + ", foreignAdultFare=" + foreignAdultFare + ", foreignChildFare=" + foreignChildFare
                + ", indianAdultFare=" + indianAdultFare + ", indianChildFare=" + indianChildFare
                + ", monumentDescription=" + monumentDescription + ", monumentId=" + monumentId + ", monumentImage="
                + monumentImage + ", monumentLocation=" + monumentLocation + ", monumentName=" + monumentName
                + ", monumentPOA=" + monumentPOA + ", monumentPreview=" + monumentPreview + ", monumentType="
                + monumentType + ", openingTime=" + openingTime + ", websiteLink=" + websiteLink + "]";
    }

    


    
}
