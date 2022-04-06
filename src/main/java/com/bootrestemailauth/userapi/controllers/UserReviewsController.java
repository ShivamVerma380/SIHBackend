package com.bootrestemailauth.userapi.controllers;

import com.bootrestemailauth.userapi.services.UserReviewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReviewsController {
    
    @Autowired
    public UserReviewsService userReviewsService;

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String auth,@RequestParam("monument_name") String monument_name,@RequestParam("date_of_visit")String date_of_visit,@RequestParam("user_rating") int rating ,@RequestParam("user_review")String review){
        return userReviewsService.addReview(auth, monument_name, date_of_visit, rating, review);
    }

    @PostMapping("/review/monument")
    public ResponseEntity<?> getMonumentReview(@RequestParam("monument_name") String monument_name){
        return userReviewsService.getMonumentReviews(monument_name);
    }

    @PostMapping("/review/user")
    public ResponseEntity<?> getUserReview(@RequestHeader("Authorization") String auth){
        return userReviewsService.getUserReviews(auth);
    }
}
