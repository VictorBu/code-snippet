package com.karonda.service1.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.karonda.dubboservice.GreetingService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Service
@Component
public class GreetingServiceImpl implements GreetingService {

    @Value("${server.port:0}")
    private Integer port;

    @SentinelResource(value = "greeting")
    @Override
    public String greeting() {
        return "hello from port: " + port;
    }
}
