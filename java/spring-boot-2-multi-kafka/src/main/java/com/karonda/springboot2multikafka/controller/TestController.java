package com.karonda.springboot2multikafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private KafkaTemplate kafkaOneTemplate;
    @Autowired
    private KafkaTemplate kafkaTwoTemplate;

    @RequestMapping("/send")
    @ResponseBody
    public String send() {
        final String TOPIC = "TOPIC_1";
        kafkaOneTemplate.send(TOPIC, "kafka one");
        kafkaTwoTemplate.send(TOPIC, "kafka two");

        return "success";
    }
}
