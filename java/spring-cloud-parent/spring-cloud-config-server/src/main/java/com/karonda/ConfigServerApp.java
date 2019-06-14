package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer // 开启 Config Server
@SpringBootApplication
public class ConfigServerApp {
    public static void main(String[] args){
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
