package com.lzx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQOrderResponseDeadConfig {

    public static final String ORDER_DEAD_QUEUE="order_dead_queue";

    public static final String ORDER_DEAD_EXCHANGE="order_dead_exchange";

    public static final String ROUTING_KEY="dead";

    @Bean(value = ORDER_DEAD_QUEUE)
    public Queue orderDeadQueue(){
        return new Queue(ORDER_DEAD_QUEUE,true,false,false,null);
    }

    @Bean(value = ORDER_DEAD_EXCHANGE)
    public TopicExchange orderDeadExchange(){
        return new TopicExchange(ORDER_DEAD_EXCHANGE);
    }

    @Bean
    public Binding orderDeadBinding(@Qualifier(ORDER_DEAD_QUEUE) Queue orderDeadQueue,@Qualifier(ORDER_DEAD_EXCHANGE) TopicExchange orderDeadExchange){
        return BindingBuilder.bind(orderDeadQueue).to(orderDeadExchange).with(ROUTING_KEY);
    }
}
