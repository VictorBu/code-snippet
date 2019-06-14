package com.karonda.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Value("${server.port}")
    int port;

    @Value("${version}")
    String version;

    @GetMapping("/hi")
    public String home(@RequestParam String name){
        return "Hello " + name + ", from port: " + port + ", version: " + version;
    }
}
