package com.ambisiss.kafka.listener;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.server.WebSocketServer;
import com.ambisiss.mongodb.dto.ChChatMsgInsertDto;
import com.ambisiss.mongodb.dto.ChGroupMsgInsertDto;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.entity.ChGroupMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import com.ambisiss.mongodb.service.ChGroupMessageMongoService;
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
    private ChChatMessageMongoService chatMongoService;

    @Autowired
    private ChGroupMessageMongoService groupMongoService;

    /**
     * 群组消息监听
     *
     * @param message
     */
    /*
    {
      "groupId": 1,
      "message": "你好",
      "messageTypeId": 1,
      "senderId": 426809250224799813
    }
     */
    @KafkaListener(topics = KafkaConstant.GROUP_TOPIC)
    public void listenGroup(String message, Acknowledgment ack) {
        log.info(KafkaConstant.GROUP_TOPIC + "发送聊天消息监听" + message);
        //TODO 缓存群聊信息
        ChGroupMsgInsertDto dto = JSON.parseObject(message, ChGroupMsgInsertDto.class);
        String uuid = groupMongoService.insertGroupMsg(dto);
        dto.setMessageUuid(uuid);
        //TODO kafka接收消息消费成功，服务端返回成功标志给用户端
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaGroupReceiveMsg(dto);
        ack.acknowledge();
    }

    /**
     * 私聊消息监听
     *
     * @param message
     */
    /*
      {
      "senderId": 426809250224799813,
      "receiverId": 426809436573532229,
      "messageTypeId": 1,
      "message": "你好",
      "userId": 426809436573532229
      }
     */
    @KafkaListener(topics = KafkaConstant.PERSONAL_TOPIC)
    public void listenPersonal(String message, Acknowledgment ack) {
        log.info(KafkaConstant.PERSONAL_TOPIC + "发送聊天消息监听" + message);
        //TODO 缓存私聊信息
        ChChatMsgInsertDto dto = JSON.parseObject(message, ChChatMsgInsertDto.class);
        String uuid = chatMongoService.insertMessage(dto);
        dto.setMessageUuid(uuid);
        //TODO kafka接收消息消费成功 服务端返回成功标志给用户端
        WebSocketServer socketServer = new WebSocketServer();
        socketServer.kafkaPersonalReceiveMsg(dto);
        ack.acknowledge();
    }
}
