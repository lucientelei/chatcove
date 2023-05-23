package com.ambisiss.mongodb.service;

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
     * @param groupMessageMongo
     * @return
     */
    int insertGroupMsg(ChGroupMessageMongo groupMessageMongo);

    /**
     * 删除群聊消息
     *
     * @param messageUuid
     * @return
     */
    int delGroupMsg(Long messageUuid);

    /**
     * 更新已读状态
     *
     * @param messageUuid
     * @param isRead
     * @return
     */
    int updateRead(String messageUuid, int isRead);

    /**
     * 查询全部群聊消息
     *
     * @return
     */
    List<ChGroupMessageMongo> listAll();

    /**
     * 查询用户未读消息
     *
     * @param userId
     * @return
     */
    List<ChGroupMessageMongo> listUnReadByUserId(Long userId);


}
