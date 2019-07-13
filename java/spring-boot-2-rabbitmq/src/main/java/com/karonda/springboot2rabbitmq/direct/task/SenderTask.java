package com.karonda.springboot2rabbitmq.direct.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Profile("direct")
@Component
@EnableAsync
public class SenderTask {

    private static final Logger logger = LoggerFactory.getLogger(SenderTask.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    private final String[] keys = {"orange", "black", "green"};

    @Async
    @Scheduled(cron = "0/1 * * * * ? ")
    public void send(){

        Random random = new Random();

        String key = keys[random.nextInt(keys.length)];

        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + " to: " + key;

        rabbitTemplate.convertAndSend(directExchange.getName(), key, message);

        logger.info(" [x] Sent '" + message + "'");
    }
}
