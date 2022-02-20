package com.bootrestemailauth.userapi.dao;


import com.bootrestemailauth.userapi.entities.MonumentRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface MonumentDao extends CrudRepository<MonumentRequest,String> {
    

    public MonumentRequest getMonumentRequestByadminId(int adminId);

    public MonumentRequest getMonumentRequestBymonumentName(String monumentName);
}
