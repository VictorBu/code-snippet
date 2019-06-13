package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrix // 启用 Hystrix 熔断器
@EnableHystrixDashboard // 启用 Hystrix Dashboard
@EnableEurekaClient
@SpringBootApplication
public class EurekaRibbonClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaRibbonClientApp.class, args);
    }
}
