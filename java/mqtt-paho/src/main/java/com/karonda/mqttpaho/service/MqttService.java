package com.karonda.mqttpaho.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttService {
    void message(String topic, MqttMessage message);
}
