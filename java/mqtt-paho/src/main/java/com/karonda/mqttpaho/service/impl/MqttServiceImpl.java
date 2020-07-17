package com.karonda.mqttpaho.service.impl;

import com.karonda.mqttpaho.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttServiceImpl implements MqttService {

    @Override
    public void message(String topic, MqttMessage message) {
        System.out.println("接收消息主题:" + topic);
        System.out.println("接收消息Qos:" + message.getQos());
        System.out.println("接收消息内容:" + new String(message.getPayload()));
    }
}
