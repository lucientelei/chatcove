package com.ambisiss.kafka.listener;

import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 17:33:13
 */
@Slf4j
public class ConsumerListener {

    /**
     * 群组消息监听
     *
     * @param record
     */
    @KafkaListener(topics = KafkaConstant.GROUP_TOPIC)
    public void listenGroup(ConsumerRecord<?, ?> record) {
        log.info(KafkaConstant.GROUP_TOPIC + "发送聊天消息监听" + record.value().toString());
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaReceiveMsg(record.value().toString());
    }

    /**
     * 私聊消息监听
     *
     * @param record
     */
    @KafkaListener(topics = KafkaConstant.PERSONAL_TOPIC)
    public void listenPersonal(ConsumerRecord<?, ?> record) {
        log.info(KafkaConstant.PERSONAL_TOPIC + "发送聊天消息监听" + record.value().toString());
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaReceiveMsg(record.value().toString());
    }
}
