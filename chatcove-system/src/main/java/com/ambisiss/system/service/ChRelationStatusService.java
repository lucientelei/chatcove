package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChRelationStatusDto;
import com.ambisiss.system.entity.ChRelationStatus;
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
public interface ChRelationStatusService extends IService<ChRelationStatus> {

    /**
     * 新增关系状态
     * @param dto
     * @return
     */
    int insertStatus(ChRelationStatusDto dto);

    /**
     * 删除关系状态
     * @param id
     * @return
     */
    int delStatus(Long id);

    /**
     * 更新关系状态
     * @param id
     * @param dto
     * @return
     */
    int updateStatus(Long id, ChRelationStatusDto dto);

    /**
     * 分页多条件查询关系状态
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ChRelationStatus> listPage(ChRelationStatusDto dto, Integer pageNum, Integer pageSize);
}
