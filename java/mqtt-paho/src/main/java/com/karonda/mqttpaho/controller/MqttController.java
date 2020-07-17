package com.karonda.mqttpaho.controller;

import com.karonda.mqttpaho.mqtt.MqttProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    @Autowired
    private MqttProducer mqttProducer;

    @RequestMapping("/send")
    public void send() {

        mqttProducer.send("test content");

    }
}
