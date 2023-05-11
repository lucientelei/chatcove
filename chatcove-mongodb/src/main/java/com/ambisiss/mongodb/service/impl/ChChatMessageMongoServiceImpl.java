package com.ambisiss.mongodb.service.impl;

import com.ambisiss.mongodb.entity.ChChatMessage;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-6 10:18:47
 */
@Service
public class ChChatMessageMongoServiceImpl implements ChChatMessageMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int insertMessage(ChChatMessage chatMessage) {
        ChChatMessage message = mongoTemplate.insert(chatMessage);
        if (StringUtils.isEmpty(message.getId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<ChChatMessage> listAll() {
        List<ChChatMessage> messageList = mongoTemplate.findAll(ChChatMessage.class);
        return messageList;
    }

    @Override
    public int delMessage(String messageUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("message_uuid").is(messageUuid));
        ChChatMessage chatMessage = mongoTemplate.findAndRemove(query, ChChatMessage.class);
        if (!StringUtils.isEmpty(chatMessage)) {
            return 0;
        }
        return 1;
    }
}
