package com.bootrestemailauth.userapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Entity
@Component
@Table(name = "live_stream")
public class LiveStreamDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stream_id")
    private int id;

    @Column(name = "application_id", unique = true)
    private String application_id;

    @Column(name = "monument_name", unique = true)
    private String monumentname;

    @Lob
    @Column(name = "resource_uri")
    private String resource;

    public LiveStreamDetails(int id, String application_id, String monument_name, String resource) {
        this.id = id;
        this.application_id = application_id;
        this.monumentname = monument_name;
        this.resource = resource;
    }

    public LiveStreamDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getMonument_name() {
        return monumentname;
    }

    public void setMonument_name(String monument_name) {
        this.monumentname = monument_name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    
}
