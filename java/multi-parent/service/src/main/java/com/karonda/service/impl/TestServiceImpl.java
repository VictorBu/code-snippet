package com.karonda.service.impl;

import com.karonda.dao.TestDao;
import com.karonda.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestDao testDao;

    @Override
    public void Test() {
        testDao.Test();
    }
}
