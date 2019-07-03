package com.karonda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class GatewayServerApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayServerApp.class, args);
    }
}
