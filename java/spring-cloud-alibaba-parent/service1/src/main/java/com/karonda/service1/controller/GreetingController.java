package com.karonda.service1.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GreetingController {

    @Value("${server.port:0}")
    private Integer port;

    @RequestMapping("/greeting")
    public String greeting() {
        return "hello from port: " + port;
    }
}
