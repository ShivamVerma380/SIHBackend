package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.Monument;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface MonumentDao extends CrudRepository<Monument,String> {
    

    public Monument getMonumentBymonumentId(String monumentId);

    public Monument getMonumentBymonumentName(String monumentName);
}
