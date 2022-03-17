package com.bootrestemailauth.userapi.dao;

import com.bootrestemailauth.userapi.entities.BlockedUsers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface BlockedUsersDao extends CrudRepository<BlockedUsers,Long>{

    public BlockedUsers getBlockedUsersByid(int id);

    public BlockedUsers getBlockedUsersByemail(String email);

}
