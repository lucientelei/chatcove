package com.ambisiss.kafka.listener;

import com.ambisiss.kafka.consumer.SocketConsumer;
import org.springframework.stereotype.Component;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 21:06:04
 */
@Component
public class ConsumerLinster {

    public ConsumerLinster(){
        System.out.println("启动kafka监听器");
        SocketConsumer socketConsumer = new SocketConsumer();
        socketConsumer.start();
        System.out.println("kafka监听器启动成功");
    }
}
