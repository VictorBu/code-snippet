package com.karonda.dao.impl;

import com.karonda.dao.TestDao;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
public class TestDaoImpl implements TestDao {

    public void Test() {

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS")));

    }
}
