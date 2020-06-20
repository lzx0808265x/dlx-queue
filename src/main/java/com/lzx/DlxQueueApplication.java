package com.lzx;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class DlxQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlxQueueApplication.class, args);
    }

}
