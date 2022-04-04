package com.bootrestemailauth.userapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_reviews")
public class UserReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    public int review_id;

    @Column(name = "user_id")
    public int user_id;

    @Column(name = "monument_id")
    public int monument_id;

    @Column(name = "date_of_visit")
    private int rating;

    @Column(name = "user_review")
    private String review;

    public UserReviews() {
    }

    public UserReviews(int review_id, int user_id, int monument_id, int rating, String review) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.monument_id = monument_id;
        this.rating = rating;
        this.review = review;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "UserReviews [monument_id=" + monument_id + ", rating=" + rating + ", review=" + review + ", review_id="
                + review_id + ", user_id=" + user_id + "]";
    }



    
    
}
