package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChGroupMessageDto;
import com.ambisiss.common.vo.ChGroupMessageVo;
import com.ambisiss.system.entity.ChGroupMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
public interface ChGroupMessageService extends IService<ChGroupMessage> {

    /**
     * 新增群组消息记录
     * @param dto
     * @return
     */
    int insertMessage(ChGroupMessageDto dto);

    /**
     * 删除消息
     * @param id
     * @return
     */
    int delMessage(Long id);

    /**
     * 获取群组聊天信息
     * @param groupId
     * @return
     */
    List<ChGroupMessageVo> listMessage(Long groupId);
}
