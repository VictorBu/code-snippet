package com.karonda.springboogmqtt.mqtt;

import com.karonda.springboogmqtt.config.MqttConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class MqttConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConsumer.class);

    @Bean
    @ServiceActivator(inputChannel = MqttConfig.INBOUND_CHANNEL)
    public MessageHandler handler() {
        return message -> {
            String topic = message.getHeaders().get(MqttConfig.RECEIVED_TOPIC_KEY).toString();
            LOGGER.info("[{}]主题接收到消息:{}", topic, message.getPayload().toString());
        };
    }
}
