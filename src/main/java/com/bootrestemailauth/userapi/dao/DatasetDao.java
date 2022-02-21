package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.DatasetRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface DatasetDao extends CrudRepository<DatasetRequest, Integer>{

    public DatasetRequest getDatasetRequestByid(int id);
    

}
