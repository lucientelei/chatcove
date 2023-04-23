package com.ambisiss.system.service;

import com.ambisiss.common.bo.ChFriendRelationshipBo;
import com.ambisiss.common.dto.ChFriendRelationshipDto;
import com.ambisiss.system.entity.ChFriendRelationship;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-23
 */
public interface ChFriendRelationshipService extends IService<ChFriendRelationship> {

    /**
     * 新增好友关系
     * TODO 双向
     * @param dto
     * @return
     */
    int insertShip(ChFriendRelationshipDto dto);

    /**
     * 删除好友关系
     * @param id
     * @return
     */
    int delShip(Long id);

    /**
     * 更新好友关系状态
     * @param id
     * @param statusId
     * @return
     */
    int updateStatus(Long id, Long statusId);

    /**
     * 更新好友置顶状态
     * @param id
     * @param isTop
     * @return
     */
    int updateIsTop(Long id, Integer isTop);

    /**
     * 获取用户好友列表
     * @param userId
     * @return
     */
    List<ChFriendRelationshipBo> listFriend(Long userId);
}
