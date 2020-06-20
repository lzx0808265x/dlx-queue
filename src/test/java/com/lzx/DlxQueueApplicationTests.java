package com.lzx;

import com.lzx.config.MQOrderResponseConfig;
import com.lzx.producter.RabbitMQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DlxQueueApplicationTests {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Test
    void contextLoads() {
        //rabbitMQSender.sendMessage(MQOrderResponseConfig.ORDER_EXCHANGE,"aaorder.#","12345");
    }

}
