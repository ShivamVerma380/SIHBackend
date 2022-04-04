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
@Table(name="user_reviews")
public class UserReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    public int id;

    @Column(name = "user_id")
    public int userid;

    @Column(name = "monument_id")
    public int monumentid;

    @Column(name = "user_rating")
    private int user_rating;

    @Column(name = "date_of_visit")
    private Date date_of_visit;
    
    @Column(name = "user_review")
    private String review;

    public UserReviews() {
    }

    public UserReviews(int id, int userid, int monumentid, int user_rating, Date date_of_visit, String review) {
        this.id = id;
        this.userid = userid;
        this.monumentid = monumentid;
        this.user_rating = user_rating;
        this.date_of_visit = date_of_visit;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMonumentid() {
        return monumentid;
    }

    public void setMonumentid(int monumentid) {
        this.monumentid = monumentid;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    public Date getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(Date date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "UserReviews [date_of_visit=" + date_of_visit + ", id=" + id + ", monumentid=" + monumentid + ", review="
                + review + ", user_rating=" + user_rating + ", userid=" + userid + "]";
    }

    


    
    
}
