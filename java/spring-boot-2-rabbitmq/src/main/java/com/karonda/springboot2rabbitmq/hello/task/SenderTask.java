package com.karonda.springboot2rabbitmq.hello.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Profile("hello")
@Component
@EnableAsync
public class SenderTask {

    private static final Logger logger = LoggerFactory.getLogger(SenderTask.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    @Async
    @Scheduled(cron = "0/1 * * * * ? ")
    public void send(){
        String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        rabbitTemplate.convertAndSend(queue.getName(), message);

        logger.info(" [x] Sent '" + message + "'");
    }
}
