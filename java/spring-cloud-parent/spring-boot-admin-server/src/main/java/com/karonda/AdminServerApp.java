package com.karonda;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAdminServer // 开启 Admin Server
@EnableEurekaClient
@SpringBootApplication
public class AdminServerApp {
    public static void main(String[] args){
        SpringApplication.run(AdminServerApp.class, args);
    }
}
