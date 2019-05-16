package com.karonda.cacheredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheredisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheredisApplication.class, args);
    }

}
