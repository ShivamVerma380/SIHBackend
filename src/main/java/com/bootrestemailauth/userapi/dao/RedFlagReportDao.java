package com.bootrestemailauth.userapi.dao;

import java.util.List;

import com.bootrestemailauth.userapi.entities.RedFlagReports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface RedFlagReportDao extends CrudRepository<RedFlagReports,Long>{

    public RedFlagReports getRedFlagReportsByreportId(int id);

    public List<RedFlagReports> getRedFlagReportsByuserEmail(String userEmail);
}
