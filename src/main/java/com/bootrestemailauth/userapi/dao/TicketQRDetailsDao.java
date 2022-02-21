package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.TicketQrRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Component
public interface TicketQRDetailsDao extends CrudRepository<TicketQrRequest, Integer>{
    // public TicketQrRequest getTicketQrRequestBymonument_id(int monument_id);
    // public TicketQrRequest getTicketQrRequestByuser_id(int user_id);
    public TicketQrRequest getTicketQrRequestByid(int id);

    public List<TicketQrRequest> getTicketQrRequestByuserId(int userId);
}
