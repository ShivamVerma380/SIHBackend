package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.LiveStreamDetails;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface LiveStreamDao extends CrudRepository<LiveStreamDetails,Long>{
    
    public LiveStreamDetails getLiveStreamDetailsBymonumentname(String monumentname);

    public LiveStreamDetails getLiveStreamDetailsByid(int id);
}
