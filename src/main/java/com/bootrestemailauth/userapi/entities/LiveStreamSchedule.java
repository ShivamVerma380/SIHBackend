package com.bootrestemailauth.userapi.entities;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "liveStreamSchedule")
public class LiveStreamSchedule {
    @Id
    @Column(name = "monument_name")
    String monumentname;

    @Column(name = "date_of_live")
    Date date_of_live;

    @Column(name = "time_of_live")
    Time time_of_live;

    public LiveStreamSchedule() {
    }

    public LiveStreamSchedule(String monument_name, Date date_of_live, Time time_of_live) {
        this.monumentname = monument_name;
        this.date_of_live = date_of_live;
        this.time_of_live = time_of_live;
    }

    public String getMonument_name() {
        return monumentname;
    }

    public void setMonument_name(String monument_name) {
        this.monumentname = monument_name;
    }

    public Date getDate_of_live() {
        return date_of_live;
    }

    public void setDate_of_live(Date date_of_live) {
        this.date_of_live = date_of_live;
    }

    public Time getTime_of_live() {
        return time_of_live;
    }

    public void setTime_of_live(Time time_of_live) {
        this.time_of_live = time_of_live;
    }
    
}
