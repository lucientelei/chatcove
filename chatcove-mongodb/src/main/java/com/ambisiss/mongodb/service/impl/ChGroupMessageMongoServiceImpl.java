package com.ambisiss.mongodb.service.impl;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.utils.MessageUUIDGenerator;
import com.ambisiss.mongodb.dto.ChGroupMsgInsertDto;
import com.ambisiss.mongodb.entity.ChGroupMessageMongo;
import com.ambisiss.mongodb.entity.GroupMessage;
import com.ambisiss.mongodb.service.ChGroupMessageMongoService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 16:53:29
 */
@Service
public class ChGroupMessageMongoServiceImpl implements ChGroupMessageMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String USER_ID = "userId";

    private final static String GROUP_MESSAGE_LIST = "groupMessageList";

    private final static String MESSAGE_UUID = "messageUuid";

    private final static String COLLECTION_NAME = "ch_group_message";

    @Override
    public int insertGroupMsg(ChGroupMsgInsertDto dto) {
        //获取用户ID
        Long userId = dto.getUserId();
        GroupMessage groupMessageAdd = generateGroupMessage(dto);
        Criteria criteria = Criteria.where(USER_ID).is(userId);
        Query query = new Query().addCriteria(criteria);
        ChGroupMessageMongo messageMongo = mongoTemplate.findOne(query, ChGroupMessageMongo.class, COLLECTION_NAME);
        //第一次添加
        if (messageMongo == null) {
            List<GroupMessage> groupMessages = new ArrayList<>();
            groupMessages.add(groupMessageAdd);
            Update update = new Update().set(USER_ID, userId).set(GROUP_MESSAGE_LIST, groupMessages);
            Query query1 = new Query().addCriteria(criteria);
            mongoTemplate.upsert(query1, update, ChGroupMessageMongo.class, COLLECTION_NAME);
        } else {
            //TODO 追加元素
            Criteria criteria1 = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
            Query query1 = new Query().addCriteria(criteria1);
            Update update = new Update().addToSet(GROUP_MESSAGE_LIST, groupMessageAdd);
            mongoTemplate.upsert(query1, update, ChGroupMessageMongo.class, COLLECTION_NAME);
        }
        return 1;
    }

    @Override
    public int delGroupMsg(Long userId, String messageUuid) {
        Update update = new Update();
        Document document = new Document(MESSAGE_UUID, messageUuid);
        update.pull(GROUP_MESSAGE_LIST, document);
        Query query = new Query(new Criteria().andOperator(
                Criteria.where(USER_ID).is(userId),
                Criteria.where("groupMessageList.messageUuid").is(messageUuid)
        ));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ChGroupMessageMongo.class, COLLECTION_NAME);
        return (int) updateResult.getMatchedCount();
    }

    @Override
    public int updateRead(Long userId, String messageUuid, int isRead) {
        Update update = new Update();
        update.set("groupMessageList.$.read", isRead);
        Query query = new Query(new Criteria().andOperator(
                Criteria.where(USER_ID).is(userId),
                Criteria.where("groupMessageList.messageUuid").is(messageUuid)
        ));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ChGroupMessageMongo.class, COLLECTION_NAME);
        return (int) updateResult.getModifiedCount();
    }

    @Override
    public ChGroupMessageMongo listAll(Long userId) {
        Criteria criteria = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
        Query query = new Query().addCriteria(criteria);
        ChGroupMessageMongo messageMongo = mongoTemplate.findOne(query, ChGroupMessageMongo.class, COLLECTION_NAME);
        return messageMongo;
    }

    @Override
    public ChGroupMessageMongo listUnReadByUserId(Long userId) {
        Criteria criteria = new Criteria().andOperator(Criteria.where(USER_ID).is(userId));
        Query query = new Query().addCriteria(criteria);
        ChGroupMessageMongo messageMongo = mongoTemplate.findOne(query, ChGroupMessageMongo.class, COLLECTION_NAME);
        if (messageMongo != null && messageMongo.getGroupMessageList() != null) {
            //TODO 获取未读消息重新定义list并添加
            List<GroupMessage> unReadList = messageMongo.getGroupMessageList().stream()
                    .filter(item -> item.getRead() == 0).collect(Collectors.toList());
            messageMongo.setGroupMessageList(unReadList);
        }
        return messageMongo;
    }

    /**
     * 生成groupMessage
     *
     * @param dto
     * @return
     */
    private GroupMessage generateGroupMessage(ChGroupMsgInsertDto dto) {
        GroupMessage groupMessage = new GroupMessage();
        //TODO  设置相关信息
        groupMessage.setSenderId(dto.getSenderId());
        groupMessage.setGroupId(dto.getGroupId());
        groupMessage.setMessageTypeId(dto.getMessageTypeId());
        groupMessage.setMessage(dto.getMessage());
        groupMessage.setCreateTime(LocalDateTime.now());
        groupMessage.setMessageUuid(MessageUUIDGenerator.generateUUID());
        groupMessage.setRead(0);
        return groupMessage;
    }
}
