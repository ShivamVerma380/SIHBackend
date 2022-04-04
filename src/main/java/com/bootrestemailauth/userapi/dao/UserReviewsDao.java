package com.bootrestemailauth.userapi.dao;

import java.util.List;

import com.bootrestemailauth.userapi.entities.UserReviews;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserReviewsDao extends CrudRepository<UserReviews,Long> {
    
    public UserReviews getUserReviewsByid(int id);

    public List<UserReviews> getUserReviewsByuserid(int id);

    public List<UserReviews> getUserReviewsBymonumentid(int id);

}
