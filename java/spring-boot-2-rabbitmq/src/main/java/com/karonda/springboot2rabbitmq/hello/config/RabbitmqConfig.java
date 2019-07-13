package com.karonda.springboot2rabbitmq.hello.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("hello")
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

}
