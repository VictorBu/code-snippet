package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@SpringBootApplication
@EnableHystrixDashboard // 启用 Hystrix Dashboard
@EnableTurbine // 启用 Hystrix Turbine
public class EurekaTurbineClientApp {
    public static void main(String[] args){
        SpringApplication.run(EurekaTurbineClientApp.class, args);
    }
}
