package com.karonda.springboot2shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.karonda.springboot2shardingjdbc.dao")
public class SpringBoot2ShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2ShardingJdbcApplication.class, args);
    }

}
