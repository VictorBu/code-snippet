package com.karonda.springboot2rabbitmq.topic.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("topic")
@Configuration
public class RabbitmqConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("topicExchangeTest");
    }

    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();// 创建一个非持久的，独占的自动删除队列
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1a(TopicExchange topic,
                            Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.orange.*");
    }

    @Bean
    public Binding binding1b(TopicExchange topic,
                             Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(topic).with("*.*.rabbit");
    }

    @Bean
    public Binding binding2a(TopicExchange topic,
                             Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(topic).with("lazy.#");
    }

}
