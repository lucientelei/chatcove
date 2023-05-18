package com.ambisiss.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.server.WebSocketServer;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Component("ConsumerListener")
public class ConsumerListener {

    @Autowired
//    @Qualifier(value = "messageMongoService")
    private ChChatMessageMongoService messageMongoService;

    /**
     * 群组消息监听
     *
     * @param message
     */
    @KafkaListener(topics = KafkaConstant.GROUP_TOPIC)
    public void listenGroup(String message, Acknowledgment ack) {
        log.info(KafkaConstant.GROUP_TOPIC + "发送聊天消息监听" + message);
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        ack.acknowledge();
//        WebSocketServer socketServer = new WebSocketServer();
//        socketServer.kafkaReceiveMsg(message);
        //TODO 群聊缓存消息
//        JSON.parseObject(message, ChChatMessage.class);
//        messageMongoService.insertMessage(chChatMessage);
    }

    /**
     * 私聊消息监听
     *
     * @param message
     */
    @KafkaListener(topics = KafkaConstant.PERSONAL_TOPIC)
    public void listenPersonal(String message, Acknowledgment ack) {
        log.info(KafkaConstant.PERSONAL_TOPIC + "发送聊天消息监听" + message);
        //TODO 缓存私聊信息
        ChChatMessageMongo chChatMessageMongo = JSON.parseObject(message, ChChatMessageMongo.class);
        chChatMessageMongo.setRead(false);
        chChatMessageMongo.setMessageUuid(MessageUUIDGenerator.generateUUID());
        chChatMessageMongo.setCreateTime(LocalDateTime.now());
        messageMongoService.insertMessage(chChatMessageMongo);
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        WebSocketServer socketServer =new WebSocketServer();
        socketServer.kafkaPersonalReceiveMsg(chChatMessageMongo);
        ack.acknowledge();
    }
}
