package com.ambisiss.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.server.WebSocketServer;
import com.ambisiss.mongodb.entity.ChChatMessage;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 17:33:13
 */
@Slf4j
@Component
public class ConsumerListener {

    @Autowired
    private ChChatMessageMongoService messageMongoService;

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
//        JSON.parseObject(record, ChChatMessage.class);
//        messageMongoService.insertMessage(chChatMessage);
    }

    /**
     * 私聊消息监听
     *
     * @param record
     */
    @KafkaListener(topics = KafkaConstant.PERSONAL_TOPIC)
    public void listenPersonal(String record, Acknowledgment ack) {
        log.info(KafkaConstant.PERSONAL_TOPIC + "发送聊天消息监听" + record);
        //TODO 缓存私聊信息
        ChChatMessage chChatMessage = JSON.parseObject(record, ChChatMessage.class);
        chChatMessage.setMessageUuid(MessageUUIDGenerator.generateUUID());
        chChatMessage.setCreateTime(LocalDateTime.now());
        messageMongoService.insertMessage(chChatMessage);
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaReceiveMsg(record);
        ack.acknowledge();
    }
}
