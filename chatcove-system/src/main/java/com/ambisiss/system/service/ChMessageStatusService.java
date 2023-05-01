package com.ambisiss.system.service;

import com.ambisiss.system.entity.ChMessageStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
public interface ChMessageStatusService extends IService<ChMessageStatus> {

    /**
     * 新增消息状态
     *
     * @param dto
     * @return
     */
    int insertStatus(ChMessageStatus dto);

    /**
     * 删除消息状态
     *
     * @param id
     * @return
     */
    int delStatus(Long id);

    /**
     * 更新消息状态
     *
     * @param dto
     * @return
     */
    int updateStatus(ChMessageStatus dto);

    /**
     * 分页查询消息状态
     *
     * @param dto
     * @return
     */
    List<ChMessageStatus> listPage(ChMessageStatus dto, Integer pageNum, Integer pageSize);
}
