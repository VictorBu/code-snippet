package com.karonda.springboot2datasourcesmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBoot2DatasourcesMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2DatasourcesMybatisApplication.class, args);
    }

}
