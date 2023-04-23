package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChFriendshipApplyDto;
import com.ambisiss.system.entity.ChFriendshipApply;
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
public interface ChFriendshipApplyService extends IService<ChFriendshipApply> {

    /**
     * 添加申请记录
     * @param dto
     * @return
     */
    int insertApply(ChFriendshipApplyDto dto);

    /**
     * 删除申请记录
     * @param id
     * @return
     */
    int delApply(Long id);

    /**
     * 更新申请状态
     * @param id
     * @param statusId
     * @return
     */
    int updateStatus(Long id, Long statusId);

    /**
     * 用户ID 既是发送申请也是接收申请
     * @param userId
     * @return
     */
    List<ChFriendshipApply> listApply(Long userId);
}
