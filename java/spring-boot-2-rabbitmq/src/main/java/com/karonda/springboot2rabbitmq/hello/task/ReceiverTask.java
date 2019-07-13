package com.karonda.springboot2rabbitmq.hello.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("hello")
@Component
@RabbitListener(queues = "hello")
public class ReceiverTask {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverTask.class);

    @RabbitHandler
    public void receive(String in){
        logger.info(" [x] Received '" + in + "'");
    }
}
