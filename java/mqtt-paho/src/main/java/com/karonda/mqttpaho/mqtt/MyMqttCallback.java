package com.karonda.mqttpaho.mqtt;

import com.karonda.mqttpaho.service.MqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyMqttCallback implements MqttCallbackExtended {

    @Value("${mqtt.consumer.consumerTopics}")
    private String[] consumerTopics;

    @Autowired
    private MqttService mqttService;

    private MqttClient mqttClient;

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("连接断开");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        mqttService.message(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }


    @Override
    public void connectComplete(boolean b, String s) {
        try {
            mqttClient.subscribe(consumerTopics);
        } catch (MqttException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
}
