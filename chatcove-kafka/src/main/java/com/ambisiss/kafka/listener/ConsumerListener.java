package com.ambisiss.kafka.listener;

import com.ambisiss.kafka.consumer.SocketConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: chenxiaoye
 * @Description: kafka消费者监听器
 * @Data: 2023-4-27 21:06:04
 */
@Slf4j
@Component
public class ConsumerListener {

    public ConsumerListener() {
        SocketConsumer socketConsumer = new SocketConsumer();
        socketConsumer.start();
        log.info("kafka监听器启动成功");
    }
}
