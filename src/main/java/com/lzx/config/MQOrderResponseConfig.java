package com.lzx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQOrderResponseConfig {

    public static final String ORDER_QUEUE="order_queue";

    public static final String ORDER_EXCHANGE="order_exchange";

    @Bean(value = ORDER_QUEUE)
    public Queue orderQueue(){
        //死信队列
        Map<String,Object> map=new HashMap<>();
        map.put("x-dead-letter-exchange",MQOrderResponseDeadConfig.ORDER_DEAD_EXCHANGE); //设置死信交换机
        map.put("x-dead-letter-routing-key",MQOrderResponseDeadConfig.ROUTING_KEY); //设置死信routingKey
        return new Queue(ORDER_QUEUE,true,false,false,map);
    }

    @Bean(value = ORDER_EXCHANGE)
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding orderBinding(@Qualifier(ORDER_QUEUE) Queue orderQueue, @Qualifier(ORDER_EXCHANGE) TopicExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order.#");
    }

}
