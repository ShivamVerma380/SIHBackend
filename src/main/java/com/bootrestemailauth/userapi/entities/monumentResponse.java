package com.bootrestemailauth.userapi.entities;

import java.sql.Time;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.stereotype.Component;

@Component
public class monumentResponse {
    private String monumentName;
    private String monumentDesc;
    @JsonBackReference
    private byte[] monumentImg;
    @JsonBackReference
    private byte[] monumentVideo;
    private String monumentLink;
    private Time startTime;
    private Time closeTime;
    private double indian_adult;
    private double indian_child;
    private double foreign_adult;
    private double foreign_child;
    private String location;

    
    public monumentResponse() {
    }

    public monumentResponse(String monumentName, byte[] monumentImg) {
        this.monumentName = monumentName;
        this.monumentImg = monumentImg;
    }

    public monumentResponse(String monumentName, String monumentDesc, byte[] monumentImg, byte[] monumentVideo,
            String monumentLink, Time startTime, Time closeTime, double indian_adult, double indian_child,
            double foreign_adult, double foreign_child, String location) {
        this.monumentName = monumentName;
        this.monumentDesc = monumentDesc;
        this.monumentImg = monumentImg;
        this.monumentVideo = monumentVideo;
        this.monumentLink = monumentLink;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.indian_adult = indian_adult;
        this.indian_child = indian_child;
        this.foreign_adult = foreign_adult;
        this.foreign_child = foreign_child;
        this.location = location;
    }
    public String getMonumentName() {
        return monumentName;
    }
    public void setMonumentName(String monumentName) {
        this.monumentName = monumentName;
    }
    public String getMonumentDesc() {
        return monumentDesc;
    }
    public void setMonumentDesc(String monumentDesc) {
        this.monumentDesc = monumentDesc;
    }
    public byte[] getMonumentImg() {
        return monumentImg;
    }
    public void setMonumentImg(byte[] monumentImg) {
        this.monumentImg = monumentImg;
    }
    public byte[] getMonumentVideo() {
        return monumentVideo;
    }
    public void setMonumentVideo(byte[] monumentVideo) {
        this.monumentVideo = monumentVideo;
    }
    public String getMonumentLink() {
        return monumentLink;
    }
    public void setMonumentLink(String monumentLink) {
        this.monumentLink = monumentLink;
    }
    public Time getStartTime() {
        return startTime;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public Time getCloseTime() {
        return closeTime;
    }
    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }
    public double getIndian_adult() {
        return indian_adult;
    }
    public void setIndian_adult(double indian_adult) {
        this.indian_adult = indian_adult;
    }
    public double getIndian_child() {
        return indian_child;
    }
    public void setIndian_child(double indian_child) {
        this.indian_child = indian_child;
    }
    public double getForeign_adult() {
        return foreign_adult;
    }
    public void setForeign_adult(double foreign_adult) {
        this.foreign_adult = foreign_adult;
    }
    public double getForeign_child() {
        return foreign_child;
    }
    public void setForeign_child(double foreign_child) {
        this.foreign_child = foreign_child;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "monumentResponse [closeTime=" + closeTime + ", foreign_adult=" + foreign_adult + ", foreign_child="
                + foreign_child + ", indian_adult=" + indian_adult + ", indian_child=" + indian_child + ", location="
                + location + ", monumentDesc=" + monumentDesc + ", monumentImg=" + Arrays.toString(monumentImg)
                + ", monumentLink=" + monumentLink + ", monumentName=" + monumentName + ", monumentVideo="
                + Arrays.toString(monumentVideo) + ", startTime=" + startTime + "]";
    }

    

}
