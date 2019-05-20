package com.karonda.app.task;

import com.karonda.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

    @Autowired
    TestService testService;

    @Scheduled(cron = "0/1 * * * * ? ")
    public void test(){
        testService.Test();
    }
}
