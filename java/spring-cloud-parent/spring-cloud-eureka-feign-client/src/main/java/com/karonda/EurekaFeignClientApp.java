package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients // 开启 Feign Client
@EnableHystrixDashboard // 启用 Hystrix Dashboard
@EnableCircuitBreaker // 启用 Hystrix 熔断器
@EnableEurekaClient
@SpringBootApplication
public class EurekaFeignClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaFeignClientApp.class, args);
    }
}
