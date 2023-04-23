package com.ambisiss.system.service;

import com.ambisiss.system.entity.ChMessageType;
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
public interface ChMessageTypeService extends IService<ChMessageType> {

    /**
     * 新增消息类型
     * @param dto
     * @return
     */
    int insertType(ChMessageType dto);

    /**
     * 删除消息类型
     * @param id
     * @return
     */
    int delType(Long id);

    /**
     * 更新消息类型
     * @param dto
     * @return
     */
    int updateType(ChMessageType dto);

    /**
     * 分页查询
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ChMessageType> listPage(ChMessageType dto, Integer pageNum, Integer pageSize);
}
