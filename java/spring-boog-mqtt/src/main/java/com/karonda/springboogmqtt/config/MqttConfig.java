package com.karonda.springboogmqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    public static final String OUTBOUND_CHANNEL = "outboundChannel";
    public static final String INBOUND_CHANNEL = "inboundChannel";

    public static final String RECEIVED_TOPIC_KEY = "mqtt_receivedTopic";

    @Value("${spring.mqtt.client.username}")
    private String username;
    @Value("${spring.mqtt.client.password}")
    private String password;
    @Value("${spring.mqtt.client.serverURIs}")
    private String[] serverURIs;
    @Value("${spring.mqtt.client.clientId}")
    private String clientId;
    @Value("${spring.mqtt.client.keepAliveInterval}")
    private int keepAliveInterval;
    @Value("${spring.mqtt.client.connectionTimeout}")
    private int connectionTimeout;

    @Value("${spring.mqtt.producer.defaultQos}")
    private int defaultProducerQos;
    @Value("${spring.mqtt.producer.defaultRetained}")
    private boolean defaultRetained;
    @Value("${spring.mqtt.producer.defaultTopic}")
    private String defaultTopic;

    @Value("${spring.mqtt.consumer.defaultQos}")
    private int defaultConsumerQos;
    @Value("${spring.mqtt.consumer.completionTimeout}")
    private long completionTimeout;
    @Value("${spring.mqtt.consumer.consumerTopics}")
    private String[] consumerTopics;

    /* 客户端 */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(serverURIs);
        mqttConnectOptions.setKeepAliveInterval(keepAliveInterval);
        mqttConnectOptions.setConnectionTimeout(connectionTimeout);

        return mqttConnectOptions;
    }

    @Bean
    public MqttPahoClientFactory getMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());

        return factory;
    }

    /* 发布者 */

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL)
    public MessageHandler getMqttProducer() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId + "_producer", getMqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        messageHandler.setDefaultRetained(defaultRetained);
        messageHandler.setDefaultQos(defaultProducerQos);

        return messageHandler;
    }

    /* 订阅者 */

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer getMqttConsumer() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId + "_consumer", getMqttClientFactory(), consumerTopics);
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultConsumerQos);
        adapter.setOutputChannel(inboundChannel());

        return adapter;
    }
}
