package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChChatMessageDto;
import com.ambisiss.common.dto.ChChatMessagePageDto;
import com.ambisiss.system.entity.ChChatMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-05-01
 */
public interface ChChatMessageService extends IService<ChChatMessage> {

    /**
     * 新增聊天记录
     * @param dto
     * @return
     */
    int insertMessage(ChChatMessageDto dto);

    /**
     * 删除聊天记录
     * @param ids
     * @return
     */
    int delMessage(List<Long> ids);

    /**
     * 查询聊天记录
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ChChatMessage> listMessage(ChChatMessagePageDto dto, Integer pageNum, Integer pageSize);

}
