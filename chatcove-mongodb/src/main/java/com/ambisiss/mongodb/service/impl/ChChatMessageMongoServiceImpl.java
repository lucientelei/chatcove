package com.ambisiss.mongodb.service.impl;

import com.ambisiss.common.constant.MessageReadConstant;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.mongodb.dto.ChChatMsgInsertDto;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.entity.ChatMessage;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-6 10:18:47
 */
@Service
public class ChChatMessageMongoServiceImpl implements ChChatMessageMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String USER_ID = "userId";
    private final static String CHAT_MESSAGE_LIST = "chatMessageList";
    private final static String MESSAGE_UUID = "messageUuid";
    private final static String COLLECTION_NAME = "ch_chat_message";


    @Override
    public String insertMessage(ChChatMsgInsertDto dto) {
        Long userId = dto.getUserId();
        ChatMessage chatMessage = generateChatMessage(dto);
        String messageUuid = MessageUUIDGenerator.generateUUID();
        chatMessage.setMessageUuid(messageUuid);
        Criteria criteria = Criteria.where(USER_ID).is(userId);
        Query query = new Query().addCriteria(criteria);
        ChChatMessageMongo messageMongo = mongoTemplate.findOne(query, ChChatMessageMongo.class, COLLECTION_NAME);
        //TODO 第一次添加 新增ChChatMessageMongo对象
        if (messageMongo == null) {
            List<ChatMessage> chatMessageList = new ArrayList<>();
            chatMessageList.add(chatMessage);
            Update update = new Update().set(USER_ID, userId).set(CHAT_MESSAGE_LIST, chatMessageList);
            Query query1 = new Query().addCriteria(criteria);
            mongoTemplate.upsert(query1, update, ChChatMessageMongo.class, COLLECTION_NAME);
        } else {
            //TODO 新增ChatMessage对象
            Criteria criteria1 = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
            Query query1 = new Query().addCriteria(criteria1);
            Update update = new Update().addToSet(CHAT_MESSAGE_LIST, chatMessage);
            mongoTemplate.upsert(query1, update, ChChatMessageMongo.class, COLLECTION_NAME);
        }
        return messageUuid;
    }

    @Override
    public ChChatMessageMongo listAll(Long userId) {
        Criteria criteria = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
        Query query = new Query().addCriteria(criteria);
        ChChatMessageMongo messageMongo = mongoTemplate.findOne(query, ChChatMessageMongo.class, COLLECTION_NAME);
        return messageMongo;
    }

    @Override
    public int delMessage(Long userId, String messageUuid) {
        Document document = new Document(MESSAGE_UUID, messageUuid);
        Update update = new Update().pull(CHAT_MESSAGE_LIST, document);
        Query query = new Query(new Criteria().andOperator(
                Criteria.where(USER_ID).is(userId),
                Criteria.where(CHAT_MESSAGE_LIST + "." + MESSAGE_UUID).is(messageUuid)
        ));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ChChatMessageMongo.class, COLLECTION_NAME);
        return (int) updateResult.getModifiedCount();
    }

    @Override
    public int updateRead(Long userId, String messageUuid, int isRead) {
        Update update = new Update().set(CHAT_MESSAGE_LIST + ".$.read", isRead);
        Query query = new Query(new Criteria().andOperator(
                Criteria.where(USER_ID).is(userId),
                Criteria.where(CHAT_MESSAGE_LIST + "." + MESSAGE_UUID).is(messageUuid)
        ));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ChChatMessageMongo.class, COLLECTION_NAME);
        return (int) updateResult.getModifiedCount();
    }

    @Override
    public ChChatMessageMongo listUnReadByUserId(Long userId) {
        Criteria criteria = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
        Query query = new Query().addCriteria(criteria);
        ChChatMessageMongo messageMongo = mongoTemplate.findOne(query, ChChatMessageMongo.class, COLLECTION_NAME);
        if (messageMongo != null && messageMongo.getChatMessageList() != null) {
            //TODO 获取未读消息并返回
            List<ChatMessage> unReadList = messageMongo.getChatMessageList().stream()
                    .filter(item -> item.getRead() == MessageReadConstant.UNREAD).collect(Collectors.toList());
            messageMongo.setChatMessageList(unReadList);
        }
        return messageMongo;
    }

    /**
     * 生成chatMessage对象
     *
     * @param dto
     * @return
     */
    private ChatMessage generateChatMessage(ChChatMsgInsertDto dto) {
        ChatMessage message = new ChatMessage();
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setMessage(dto.getMessage());
        message.setMessageTypeId(dto.getMessageTypeId());
        message.setRead(MessageReadConstant.UNREAD);
        message.setCreateTime(LocalDateTime.now());
        return message;
    }
}
