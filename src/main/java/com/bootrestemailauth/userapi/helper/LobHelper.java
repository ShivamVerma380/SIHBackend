package com.bootrestemailauth.userapi.helper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Clob;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LobHelper {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Blob createBlob(InputStream content, long size) {
        return ((Session)entityManager).getLobHelper().createBlob(content, size);
    }


}
