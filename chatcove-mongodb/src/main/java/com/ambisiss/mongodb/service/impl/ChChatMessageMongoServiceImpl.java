package com.ambisiss.mongodb.service.impl;

import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    public int insertMessage(ChChatMessageMongo chatMessage) {
        ChChatMessageMongo message = mongoTemplate.insert(chatMessage);
        if (StringUtils.isEmpty(message.getId())) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<ChChatMessageMongo> listAll() {
        return mongoTemplate.findAll(ChChatMessageMongo.class);
    }

    @Override
    public int delMessage(String messageUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("message_uuid").is(messageUuid));
        ChChatMessageMongo chatMessage = mongoTemplate.findAndRemove(query, ChChatMessageMongo.class);
        if (!StringUtils.isEmpty(chatMessage)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int updateRead(String messageUuid, int isRead) {
        Query query = new Query().addCriteria(Criteria.where("message_uuid").is(messageUuid));
        Update update = new Update().set("read", isRead);
        UpdateResult result = mongoTemplate.upsert(query, update, ChChatMessageMongo.class);
        return (int) result.getModifiedCount();
    }

    @Override
    public List<ChChatMessageMongo> listUnReadMsg(Long userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("user_id").is(userId)
                .and("read").is(0);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, ChChatMessageMongo.class);
    }
}
