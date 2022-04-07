package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.LiveStreamSchedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface LiveSceduleDao extends CrudRepository<LiveStreamSchedule,String>{
    public LiveStreamSchedule getLiveStreamScheduleBymonumentname(String monumentName);
}
