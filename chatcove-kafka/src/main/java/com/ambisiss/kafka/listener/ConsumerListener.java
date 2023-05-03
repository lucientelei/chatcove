package com.ambisiss.kafka.listener;

import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 17:33:13
 */
@Slf4j
@Component
public class ConsumerListener {

    /**
     * 群组消息监听
     *
     * @param record
     */
    @KafkaListener(topics = KafkaConstant.GROUP_TOPIC)
    public void listenGroup(String record, Acknowledgment ack) {
        log.info(KafkaConstant.GROUP_TOPIC + "发送聊天消息监听" + record);
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        ack.acknowledge();
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaReceiveMsg(record);
        //TODO 群聊缓存消息
    }

    /**
     * 私聊消息监听
     *
     * @param record
     */
    @KafkaListener(topics = KafkaConstant.PERSONAL_TOPIC)
    public void listenPersonal(String record, Acknowledgment ack) {
        log.info(KafkaConstant.PERSONAL_TOPIC + "发送聊天消息监听" + record);
        ack.acknowledge();
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaReceiveMsg(record);
        //TODO 缓存私聊信息
    }
}
