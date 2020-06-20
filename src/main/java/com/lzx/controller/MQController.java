package com.lzx.controller;

import com.lzx.producter.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQController {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @GetMapping("/send")
    public void send(){
        rabbitMQSender.sendMessage();
    }
}
