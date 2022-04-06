package com.bootrestemailauth.userapi.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.dao.UserDao;
import com.bootrestemailauth.userapi.dao.UserReviewsDao;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.bootrestemailauth.userapi.entities.UserRequest;
import com.bootrestemailauth.userapi.entities.UserReviews;
import com.bootrestemailauth.userapi.entities.UserReviewsResponse;
import com.bootrestemailauth.userapi.helper.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserReviewsService {

    @Autowired
    public UserReviews userReviews;
    
    @Autowired
    public UserReviewsDao userReviewsDao;

    @Autowired
    public UserRequest userRequest;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public MonumentRequest monumentRequest;

    @Autowired
    public UserDao userDao;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public UserReviewsResponse userReviewsResponse;

    public ResponseEntity<?> addReview(String auth,String monument_name,String date_of_visit,int rating,String review){

        try {
            String jwtToken = auth.substring(7);
            String email = jwtUtil.extractUsername(jwtToken);
            userRequest = userDao.getUserRequestByuseremail(email);
            
            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);

            
            List<UserReviews> list = (List<UserReviews>) userReviewsDao.getUserReviewsBymonumentid(monumentRequest.getMonumentId());
            Date date = Date.valueOf(date_of_visit);
            boolean flag = true;
            for(int i=0;i<list.size();i++){
                if(list.get(i).getDate_of_visit().equals(date) && list.get(i).getMonumentid()==monumentRequest.getMonumentId() && list.get(i).getUserid()==userRequest.getId()){
                    flag = false;
                    break;
                }
            }
            if(flag){
                userReviews.setUserid(userRequest.getId());
                userReviews.setMonumentid(monumentRequest.getMonumentId());
                userReviews.setDate_of_visit(date);
                userReviews.setReview(review);
                userReviews.setUser_rating(rating);

                userReviewsDao.save(userReviews);
            }
            responseMessage.setMessage("Review saved successfully");
            return ResponseEntity.ok().body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseMessage);
        }
    }

    public ResponseEntity<?> getMonumentReviews(String monument_name){
        try {
            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);
            List<UserReviews> list;
            if(monumentRequest!=null){
                list = (List<UserReviews>)userReviewsDao.getUserReviewsBymonumentid(monumentRequest.getMonumentId());
                List<UserReviewsResponse> reviews = new ArrayList<>();
                for(int i=0;i<list.size();i++){
                    userRequest = userDao.getUserRequestById(list.get(i).getUserid());
                    UserReviewsResponse userReviewsResponse = new UserReviewsResponse();


                    userReviewsResponse.setUserName(userRequest.getName());
                    userReviewsResponse.setUserRating(list.get(i).getUser_rating());
                    userReviewsResponse.setDate_of_visit(list.get(i).getDate_of_visit().toString());
                    userReviewsResponse.setUserReview(list.get(i).getReview());
                    reviews.add(userReviewsResponse);
                }
                return ResponseEntity.ok().body(reviews);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> getUserReviews(String auth){
        try {
            String jwtToken = auth.substring(7);
            String email = jwtUtil.extractUsername(jwtToken);
            userRequest = userDao.getUserRequestByuseremail(email);
            List<UserReviews> list = (List<UserReviews>) userReviewsDao.getUserReviewsByuserid(userRequest.getId());
            return ResponseEntity.status(HttpStatus.OK).body(list);          

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
