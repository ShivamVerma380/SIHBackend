package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.VisitedQrTicketsRequests;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface VisitedQrTicketDao extends CrudRepository<VisitedQrTicketsRequests,Integer>{
    
    public VisitedQrTicketsRequests getVisitedQrTicketsRequestsByid(int id);
}
    

