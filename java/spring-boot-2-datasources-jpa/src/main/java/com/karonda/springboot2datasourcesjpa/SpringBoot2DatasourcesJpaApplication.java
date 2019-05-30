package com.karonda.springboot2datasourcesjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBoot2DatasourcesJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2DatasourcesJpaApplication.class, args);
    }

}
