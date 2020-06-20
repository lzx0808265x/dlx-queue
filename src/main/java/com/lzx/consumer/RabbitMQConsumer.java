package com.lzx.consumer;

import com.lzx.config.MQOrderResponseConfig;
import com.lzx.config.MQOrderResponseDeadConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = MQOrderResponseConfig.ORDER_QUEUE)
    public void handleMessage(@Payload String body, @Headers Map<String,Object> headers, Message message, Channel channel){
        try {
            log.info("handleMessage:{}",body);
            log.info("deliveryTag:{}",message.getMessageProperties().getDeliveryTag());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = MQOrderResponseDeadConfig.ORDER_DEAD_QUEUE)
    public void handleDeadMessage(@Payload String body, @Headers Map<String,Object> headers, Message message, Channel channel){
        try {
            log.info("handleDeadMessage:{}",body);
            log.info("deliveryTag:{}",message.getMessageProperties().getDeliveryTag());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(new Jack());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
}
