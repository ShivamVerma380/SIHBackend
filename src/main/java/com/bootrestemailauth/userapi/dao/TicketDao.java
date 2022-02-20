package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.TicketRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface TicketDao extends CrudRepository<TicketRequest, Integer>{

    public TicketRequest getTicketRequestByid(int id);
}
