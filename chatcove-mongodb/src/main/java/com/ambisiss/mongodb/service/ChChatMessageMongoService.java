package com.ambisiss.mongodb.service;

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
     * @param chatMessage
     * @return
     */
    int insertMessage(ChChatMessageMongo chatMessage);

    /**
     * 查看全部消息
     * @return
     */
    List<ChChatMessageMongo> listAll();

    /**
     * 删除指定消息
     * @param messageUuid
     * @return
     */
    int delMessage(String messageUuid);

    /**
     * 更新已读状态
     * @param messageUuid
     * @param isRead
     * @return
     */
    int updateRead(String messageUuid, int isRead);
}
