package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChGroupsInsertDto;
import com.ambisiss.system.entity.ChGroups;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
public interface ChGroupsService extends IService<ChGroups> {

    /**
     * 新增群组
     * @param dto
     * @return
     */
    int insertGroup(ChGroupsInsertDto dto);

    /**
     * 解散群组
     * @param groupId
     * @return
     */
    int delGroup(Long groupId);


}
