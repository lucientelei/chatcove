package com.ambisiss.mongodb.service;

import com.ambisiss.mongodb.dto.ChChatMsgInsertDto;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-6 09:17:58
 */
public interface ChChatMessageMongoService {

    /**
     * 新增消息
     *
     * @param dto
     * @return
     */
    String insertMessage(ChChatMsgInsertDto dto);

    /**
     * 查看全部消息
     *
     * @param userId
     * @return
     */
    ChChatMessageMongo listAll(Long userId);

    /**
     * 删除指定消息
     *
     * @param userId
     * @param messageUuid
     * @return
     */
    int delMessage(Long userId, String messageUuid);

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
     * 查看用户未读消息
     *
     * @param userId
     * @return
     */
    ChChatMessageMongo listUnReadByUserId(Long userId);

    /**
     * 通过uuid获取指定信息
     * @param userId
     * @param messageUuid
     * @return
     */
//    ChChatMessageMongo getMsgByUuid(Long userId, String messageUuid);
}
