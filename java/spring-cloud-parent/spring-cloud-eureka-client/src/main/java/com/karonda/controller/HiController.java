package com.karonda.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 需要权限
    @RequestMapping("/hello")
    public String hello(){
        return "hello!";
    }
}
