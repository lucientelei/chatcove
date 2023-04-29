package com.ambisiss.kafka.consumer;

import com.ambisiss.websocket.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 21:05:04
 */
@Slf4j
public class SocketConsumer extends Thread {

    @Override
    public void run() {
        Properties prop = new Properties();
        log.info("启动kafka消费者....");
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("group.id", "socket");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //如果是之前存在的group.id
        Consumer consumer = new KafkaConsumer(prop);
        consumer.subscribe(Arrays.asList("zeek_test"));
        while (true) {
            ConsumerRecords<String, String> c = consumer.poll(100);
            for (ConsumerRecord<String, String> c1 : c) {
                log.info("ConsumerRecord:" + c1.value());
                WebSocketServer.sendMessage("socket", c1.value());
            }
        }
    }

}
