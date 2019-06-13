package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy // 开启 Zuul
@SpringBootApplication
public class EurekaZuulClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaZuulClientApp.class, args);
    }
}
