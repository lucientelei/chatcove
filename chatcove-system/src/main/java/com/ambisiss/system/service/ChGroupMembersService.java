package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChGroupMembersDto;
import com.ambisiss.common.vo.ChGroupMembersVo;
import com.ambisiss.system.entity.ChGroupMembers;
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
public interface ChGroupMembersService extends IService<ChGroupMembers> {

    /**
     * 添加群组成员
     * @param dto
     * @return
     */
    int insertMember(ChGroupMembersDto dto);

    /**
     * 删除群组成员
     * @param dto
     * @return
     */
    int delMember(ChGroupMembersDto dto);

    /**
     * 获取群组成员列表
     * @param groupId
     * @return
     */
    List<ChGroupMembersVo> listMember(Long groupId);

    /**
     * 删除群聊群组成员
     * @param groupId
     * @return
     */
    int delGroupMember(Long groupId);
}
