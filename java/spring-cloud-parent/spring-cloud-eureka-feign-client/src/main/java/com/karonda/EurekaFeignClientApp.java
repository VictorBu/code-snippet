package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // 开启 Feign Client
@EnableEurekaClient
@SpringBootApplication
public class EurekaFeignClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaFeignClientApp.class, args);
    }
}
