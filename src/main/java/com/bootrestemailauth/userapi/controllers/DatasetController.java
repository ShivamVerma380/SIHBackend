package com.bootrestemailauth.userapi.controllers;

import java.sql.Date;

import com.bootrestemailauth.userapi.dao.DatasetDao;
import com.bootrestemailauth.userapi.entities.DatasetRequest;
import com.bootrestemailauth.userapi.services.DatasetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatasetController {
    @Autowired
    public DatasetDao datasetDao;

    @Autowired
    public DatasetRequest datasetRequest;

    @Autowired
    public DatasetService datasetService;

    @PostMapping("/add-data")
    public ResponseEntity<?> addData(@RequestParam("monument_name") String monument_name,@RequestParam("date") Date date, @RequestParam("festival") boolean festival, @RequestParam("weekend") boolean weekend, @RequestParam("rain")boolean rain,@RequestParam("no_of_visitors") int no_of_visitors){
        return datasetService.addData(monument_name, date, festival, weekend, rain, no_of_visitors);
    }
}
