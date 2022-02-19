package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.MonumentVerificationRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface MonumentVerificationRequestDao extends CrudRepository<MonumentVerificationRequest,Integer> {
    
    public MonumentVerificationRequest getMonumentVerificationRequestByadminId(int adminId);
}
