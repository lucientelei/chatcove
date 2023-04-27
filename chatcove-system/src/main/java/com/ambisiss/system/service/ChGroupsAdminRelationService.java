package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChGroupsAdminRelationDto;
import com.ambisiss.system.entity.ChGroupsAdminRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-27
 */
public interface ChGroupsAdminRelationService extends IService<ChGroupsAdminRelation> {

    /**
     * 新增群组管理员关系
     * @param dto
     * @return
     */
    int insertRelation(ChGroupsAdminRelationDto dto);

    /**
     * 解除群组管理员关系
     * @param dto
     * @return
     */
    int delRelation(ChGroupsAdminRelationDto dto);

    /**
     * 获取群组管理员列表
     * @param groupId
     * @return
     */
    List<Long> getGroupAdmin(Long groupId);


}
