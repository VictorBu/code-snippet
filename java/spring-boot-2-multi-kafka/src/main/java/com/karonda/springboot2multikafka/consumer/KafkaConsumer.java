package com.karonda.springboot2multikafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    final String TOPIC = "TOPIC_1";

    // containerFactory 的值要与配置中 KafkaListenerContainerFactory 的 Bean 名相同
    @KafkaListener(topics = {TOPIC}, containerFactory = "kafkaOneContainerFactory")
    public void listenerOne(ConsumerRecord<?, ?> record) {
        LOGGER.info(" kafka one 接收到消息：{}", record.value());
    }

    @KafkaListener(topics = {TOPIC}, containerFactory = "kafkaTwoContainerFactory")
    public void listenerTwo(ConsumerRecord<?, ?> record) {
        LOGGER.info(" kafka two 接收到消息：{}", record.value());
    }
}
