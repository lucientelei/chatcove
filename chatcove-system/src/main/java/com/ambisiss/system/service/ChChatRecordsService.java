package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChChatRecordsDto;
import com.ambisiss.common.dto.ChChatRecordsPageDto;
import com.ambisiss.system.entity.ChChatRecords;
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
public interface ChChatRecordsService extends IService<ChChatRecords> {

    /**
     * 新增聊天记录
     * @param dto
     * @return
     */
    int insertRecords(ChChatRecordsDto dto);

    /**
     * 删除聊天记录
     * @param ids
     * @return
     */
    int delRecords(List<Long> ids);

    /**
     * 分页查询聊天记录
     * @param dto
     * @return
     */
    List<ChChatRecords> listRecords(ChChatRecordsPageDto dto);
}
