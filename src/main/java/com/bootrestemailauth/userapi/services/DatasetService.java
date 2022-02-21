package com.bootrestemailauth.userapi.services;

import java.sql.Date;

import com.bootrestemailauth.userapi.dao.DatasetDao;
import com.bootrestemailauth.userapi.dao.MonumentDao;
import com.bootrestemailauth.userapi.entities.DatasetRequest;
import com.bootrestemailauth.userapi.entities.MonumentRequest;
import com.bootrestemailauth.userapi.entities.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DatasetService {
    @Autowired
    public DatasetDao datasetDao;

    @Autowired
    public DatasetRequest datasetRequest;

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public MonumentDao monumentDao;

    @Autowired
    public MonumentRequest monumentRequest;

    public ResponseEntity<?> addData(String monument_name, Date date, boolean festival, boolean weekend, boolean rain, int no_of_visitors){
        try {
            monumentRequest = monumentDao.getMonumentRequestBymonumentName(monument_name);
            if(monumentRequest == null){
                responseMessage.setMessage("Monument does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
            }
            datasetRequest.setDate(date);
            datasetRequest.setFestival(festival);
            datasetRequest.setMonument_id(monumentRequest.getMonumentId());
            datasetRequest.setRain(rain);
            datasetRequest.setWeekend(weekend);
            datasetRequest.setNo_of_visitors(no_of_visitors);

            datasetDao.save(datasetRequest);
            responseMessage.setMessage("Dataset added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
