package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.AdminAccountDetails;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component
public interface AdminAccDetailsDao extends CrudRepository<AdminAccountDetails,Integer> {
    
    public AdminAccountDetails getAdminAccountDetailsByadminId(int adminId);

    public AdminAccountDetails getAdminAccountDetailsByaccountNo(String accountNo);
}
