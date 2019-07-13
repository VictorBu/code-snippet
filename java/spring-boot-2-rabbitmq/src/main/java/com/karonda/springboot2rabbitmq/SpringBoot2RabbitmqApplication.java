package com.karonda.springboot2rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBoot2RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2RabbitmqApplication.class, args);
    }

}
