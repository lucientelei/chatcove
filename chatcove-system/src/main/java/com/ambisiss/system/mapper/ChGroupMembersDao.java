package com.ambisiss.system.mapper;

import com.ambisiss.system.entity.ChGroupMembers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-27
 */
@Mapper
public interface ChGroupMembersDao extends BaseMapper<ChGroupMembers> {

    /**
     * 查询用户群组群昵称
     * @param groupId   群聊ID
     * @param memberId  用户ID
     * @return
     */
    String selectGroupNickname(Long groupId, Long memberId);
}
