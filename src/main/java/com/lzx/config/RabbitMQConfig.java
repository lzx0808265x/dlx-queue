package com.lzx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory cachingConnectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        // 必须设置为 true，不然当 发送到交换器成功，但是没有匹配的队列，不会触发 ReturnCallback 回调
        // 而且 ReturnCallback 比 ConfirmCallback 先回调，意思就是 ReturnCallback 执行完了才会执行 ConfirmCallback
        rabbitTemplate.setMandatory(true);
        // 如果发送到交换器都没有成功（比如说删除了交换器），ack 返回值为 false
        // 如果发送到交换器成功，但是没有匹配的队列（比如说取消了绑定），ack 返回值为还是 true （这是一个坑，需要注意）
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
            log.info("rabbitTemplate:{}",ack);
            if(ack){
                log.info("消息发送成功：ack");
            }else{
                log.info("消息发送失败：ack");
            }
        });
        //如果发送到交换器成功，但是没有匹配的队列，就会触发这个回调
        rabbitTemplate.setReturnCallback((msg, replyCode, replyText, exchange, routingKey)->{
            log.info("消息发送失败setReturnCallback");
        });
        return rabbitTemplate;
    }
}
