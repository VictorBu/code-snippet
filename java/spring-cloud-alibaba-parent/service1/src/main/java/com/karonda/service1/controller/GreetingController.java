package com.karonda.service1.controller;

import com.karonda.dubboservice.GreetingService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GreetingController {

//    @Reference
    @Autowired
    private GreetingService greetingService;

    @RequestMapping("/greeting")
    public String greeting() {
        return greetingService.greeting();
    }
}
