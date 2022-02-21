package com.bootrestemailauth.userapi.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "dataset")
public class DatasetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "monument_id")
    private int monument_id;

    @Column(name = "date")
    private Date date;

    @Column(name = "festival")
    private boolean festival;

    @Column(name = "weekend")
    private boolean weekend;

    @Column(name = "rain")
    private boolean rain;

    @Column(name = "no_of_visitors")
    private int no_of_visitors;

    public DatasetRequest() {
    }

    public DatasetRequest(int id, int monument_id, Date date, boolean festival, boolean weekend, boolean rain,
            int no_of_visitors) {
        this.id = id;
        this.monument_id = monument_id;
        this.date = date;
        this.festival = festival;
        this.weekend = weekend;
        this.rain = rain;
        this.no_of_visitors = no_of_visitors;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFestival() {
        return festival;
    }

    public void setFestival(boolean festival) {
        this.festival = festival;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public int getNo_of_visitors() {
        return no_of_visitors;
    }

    public void setNo_of_visitors(int no_of_visitors) {
        this.no_of_visitors = no_of_visitors;
    }

    @Override
    public String toString() {
        return "DatasetRequest [date=" + date + ", festival=" + festival + ", id=" + id + ", monument_id=" + monument_id
                + ", no_of_visitors=" + no_of_visitors + ", rain=" + rain + ", weekend=" + weekend + "]";
    }

    
    
}
