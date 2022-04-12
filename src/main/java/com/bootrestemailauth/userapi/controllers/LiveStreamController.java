package com.bootrestemailauth.userapi.controllers;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import com.bootrestemailauth.userapi.dao.LiveSceduleDao;
import com.bootrestemailauth.userapi.dao.LiveStreamDao;
import com.bootrestemailauth.userapi.entities.LiveStreamDetails;
import com.bootrestemailauth.userapi.entities.LiveStreamSchedule;
import com.bootrestemailauth.userapi.entities.ResponseMessage;
import com.nimbusds.oauth2.sdk.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LiveStreamController {
    
    @Autowired
    public LiveStreamDetails liveStreamDetails;

    @Autowired
    public LiveStreamDao liveStreamDao;

    @Autowired
    public LiveSceduleDao liveSceduleDao;

    @Autowired
    public LiveStreamSchedule liveStreamSchedule;
    @Autowired
    public ResponseMessage responseMessage;

    @PostMapping("/add-bambuser-account")
    public ResponseEntity<?> addBambUserAccount(@RequestHeader("Authorization") String Auth,@RequestParam("MonumentName") String monument_name,@RequestParam("application_id") String application_id,@RequestParam("PlayerLink") String playerLink){
        try{
            liveStreamDetails.setApplication_id(application_id);
            liveStreamDetails.setMonument_name(monument_name);
            liveStreamDetails.setResource(playerLink);
            liveStreamDao.save(liveStreamDetails);
            responseMessage.setMessage("Bambuser account added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    @GetMapping("/get-all-live-schedules")
    public ResponseEntity<?> getAllLiveSchedules(){
        try{
            List<LiveStreamSchedule> livestreamlist = new ArrayList<>();
            livestreamlist = (List<LiveStreamSchedule>) liveSceduleDao.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(livestreamlist);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getLiveStreamBymonument/{monument}")
    public ResponseEntity<?> getLiveScheduleByMonument(@PathVariable("monument") String monument){
        try{
            liveStreamSchedule = liveSceduleDao.getLiveStreamScheduleBymonumentname(monument);
            return ResponseEntity.status(HttpStatus.OK).body(liveStreamSchedule);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/addLiveStreamSchedule")
    public ResponseEntity<?> addLiveStreamSchedule(@RequestParam("monument")String monument,@RequestParam("dateOfVisit") String date,@RequestParam("timeOfVisit") String time){
        try {
            time = time.substring(0, 5)+":00";
            Time time_of_live = Time.valueOf(time);
            Date date_of_live = Date.valueOf(date);
            liveStreamSchedule.setMonument_name(monument);
            liveStreamSchedule.setDate_of_live(date_of_live);
            liveStreamSchedule.setTime_of_live(time_of_live);
            liveSceduleDao.save(liveStreamSchedule);
            responseMessage.setMessage("Live stream added successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getLiveStream/{monument}")
    public ResponseEntity<?> getResourceUri(@PathVariable("monument") String monument_name){
        try{
            LiveStreamDetails liveStream = liveStreamDao.getLiveStreamDetailsBymonumentname(monument_name);
            responseMessage.setMessage(liveStream.getResource());
            return ResponseEntity.ok().body(responseMessage);
        }catch(Exception e){
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseMessage);
        }
    }
}
