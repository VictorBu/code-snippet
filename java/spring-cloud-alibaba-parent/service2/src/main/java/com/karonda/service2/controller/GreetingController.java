package com.karonda.service2.controller;

import com.karonda.dubboservice.GreetingService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return restTemplate.getForObject("http://service1/greeting", String.class);
    }

    @Reference
    private GreetingService greetingService;

    @RequestMapping("/dubboGreeting")
    public String dubboGreeting() {
        return greetingService.greeting();
    }
}
