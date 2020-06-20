package com.lzx.producter;

import com.lzx.config.MQOrderResponseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQSender {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void sendMessage(){
        log.info("sendMessage");
        rabbitTemplate.convertAndSend(MQOrderResponseConfig.ORDER_EXCHANGE,"order.#","12345", message -> {
            MessageProperties properties=message.getMessageProperties();
            properties.setExpiration("10000");
            return message;
        });
    }

}
