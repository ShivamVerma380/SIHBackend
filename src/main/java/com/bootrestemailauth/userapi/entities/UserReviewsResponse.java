package com.bootrestemailauth.userapi.entities;

import org.springframework.stereotype.Component;

@Component
public class UserReviewsResponse {
    
    public String userName;
    public String userReview;
    public String date_of_visit;
    public int userRating;

    public UserReviewsResponse() {
    }
    public UserReviewsResponse(String userName, String userReview, String date_of_visit, int userRating) {
        this.userName = userName;
        this.userReview = userReview;
        this.date_of_visit = date_of_visit;
        this.userRating = userRating;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserReview() {
        return userReview;
    }
    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
    public String getDate_of_visit() {
        return date_of_visit;
    }
    public void setDate_of_visit(String date_of_visit) {
        this.date_of_visit = date_of_visit;
    }
    public int getUserRating() {
        return userRating;
    }
    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
    @Override
    public String toString() {
        return "UserReviewsResponse [date_of_visit=" + date_of_visit + ", userName=" + userName + ", userRating="
                + userRating + ", userReview=" + userReview + "]";
    }

    

    


}
