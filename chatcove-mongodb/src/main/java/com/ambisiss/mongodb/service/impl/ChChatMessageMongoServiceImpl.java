package com.ambisiss.mongodb.service.impl;

import com.ambisiss.mongodb.entity.ChChatMessage;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

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
//        message.
        return 1;
    }

    @Override
    public List<ChChatMessage> listAll() {
        return null;
    }

    @Override
    public int delMessage(String messageUuid) {
        return 1;
    }
}
