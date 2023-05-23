package com.ambisiss.mongodb.service.impl;

import com.ambisiss.mongodb.entity.ChGroupMessageMongo;
import com.ambisiss.mongodb.service.ChGroupMessageMongoService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 16:53:29
 */
@Service
public class ChGroupMessageMongoServiceImpl implements ChGroupMessageMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int insertGroupMsg(ChGroupMessageMongo groupMessageMongo) {
//        new Query().addCriteria(Criteria.where("user_id").is(groupMessageMongo))
        return 1;
    }

    @Override
    public int delGroupMsg(Long messageUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("message_uuid").is(messageUuid));
        ChGroupMessageMongo messageMongo = mongoTemplate.findAndRemove(query, ChGroupMessageMongo.class);
        if (!StringUtils.isEmpty(messageMongo)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int updateRead(String messageUuid, int isRead) {
        Query query = new Query().addCriteria(Criteria.where("message_uuid").is(messageUuid));
        Update update = new Update().set("read", isRead);
        UpdateResult result = mongoTemplate.upsert(query, update, ChGroupMessageMongo.class);
        return (int) result.getModifiedCount();
    }

    @Override
    public List<ChGroupMessageMongo> listAll() {
        return mongoTemplate.findAll(ChGroupMessageMongo.class);
    }

    @Override
    public List<ChGroupMessageMongo> listUnReadByUserId(Long userId) {
        Query query = new Query().addCriteria(Criteria.where("user_id").is(userId)
                .and("read").is(0));
        return mongoTemplate.find(query, ChGroupMessageMongo.class);
    }
}
