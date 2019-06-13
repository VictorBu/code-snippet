package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix // 启用 Hystrix 熔断器
@EnableEurekaClient
@SpringBootApplication
public class EurekaRibbonClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaRibbonClientApp.class, args);
    }
}
