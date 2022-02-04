package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.JwtRequest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserDao extends CrudRepository<JwtRequest,Integer>{

    public  JwtRequest getJwtRequestById(int id);

    public JwtRequest getJwtRequestByuseremail(String useremail);
    //always name methods with get_Entityname_By_variable_name;

}
