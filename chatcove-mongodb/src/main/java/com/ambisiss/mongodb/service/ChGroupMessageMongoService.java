package com.ambisiss.mongodb.service;

import com.ambisiss.mongodb.dto.ChGroupMsgInsertDto;
import com.ambisiss.mongodb.entity.ChGroupMessageMongo;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 16:25:55
 */
public interface ChGroupMessageMongoService {

    /**
     * 新增群聊消息
     *
     * @param dto
     * @return
     */
    String insertGroupMsg(ChGroupMsgInsertDto dto);

    /**
     * 删除群聊消息
     *
     * @param messageUuid
     * @return
     */
    int delGroupMsg(Long userId, String messageUuid);

    /**
     * 更新已读状态
     *
     * @param userId
     * @param messageUuid
     * @param isRead
     * @return
     */
    int updateRead(Long userId, String messageUuid, int isRead);

    /**
     * 查询全部群聊消息
     *
     * @return
     */
    ChGroupMessageMongo listAll(Long userId);

    /**
     * 查询用户未读消息
     *
     * @param userId
     * @return
     */
    ChGroupMessageMongo listUnReadByUserId(Long userId);


}
