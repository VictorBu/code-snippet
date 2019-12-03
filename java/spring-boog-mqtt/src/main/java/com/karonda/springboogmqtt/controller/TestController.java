package com.karonda.springboogmqtt.controller;

import com.karonda.springboogmqtt.mqtt.MqttSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MqttSender mqttSender;

    @RequestMapping("/send")
    private void send(String data){
        mqttSender.sendToMqtt(data);
    }
}
