package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Oauth2ServerApp {

    public static void main(String[] args){
        SpringApplication.run(Oauth2ServerApp.class, args);
    }

}
