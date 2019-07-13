package com.karonda.springboot2rabbitmq.fanout.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("fanout")
@Configuration
public class RabbitmqConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("fanoutExchangeTest");
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
    public Binding binding1(FanoutExchange fanout,
                            Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout,
                            Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
    }
}
