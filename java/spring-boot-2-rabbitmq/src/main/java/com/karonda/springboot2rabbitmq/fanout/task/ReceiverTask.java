package com.karonda.springboot2rabbitmq.fanout.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("fanout")
@Component
public class ReceiverTask {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverTask.class);

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in){
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in){
        receive(in, 2);
    }

    public void receive(String in, int receiver){
        logger.info("instance " + receiver + " [x] Received '" + in + "'");
    }
}
