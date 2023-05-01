package com.ambisiss.system.service.impl;

import com.ambisiss.common.constant.RelationEnum;
import com.ambisiss.common.dto.ChFriendshipApplyDto;
import com.ambisiss.system.entity.ChFriendshipApply;
import com.ambisiss.system.mapper.ChFriendshipApplyDao;
import com.ambisiss.system.service.ChFriendRelationshipService;
import com.ambisiss.system.service.ChFriendshipApplyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-23
 */
@Service
public class ChFriendshipApplyServiceImpl extends ServiceImpl<ChFriendshipApplyDao, ChFriendshipApply> implements ChFriendshipApplyService {

    @Autowired
    private ChFriendshipApplyDao applyDao;

    @Autowired
    private ChFriendRelationshipService friendRelationshipService;

    @Override
    public int insertApply(ChFriendshipApplyDto dto) {
        ChFriendshipApply apply = new ChFriendshipApply();
        BeanUtils.copyProperties(dto, apply);
        apply.setStatusId(RelationEnum.UNCONFIRMED.getId());
        return applyDao.insert(apply);
    }

    @Override
    public int delApply(Long id) {
        return applyDao.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, Long statusId) {
        ChFriendshipApply apply = applyDao.selectById(id);
        //TODO 接收申请人同意后插入好友关系
//        if (RelationEnum.CONFIRMED.getId().equals(statusId)) {
//            ChFriendRelationshipDto dto = new ChFriendRelationshipDto();
//            dto.setUserId(apply.getFriendId())
//            friendRelationshipService.insertShip()
//        }
        apply.setStatusId(statusId);
        return applyDao.updateById(apply);
    }

    @Override
    public List<ChFriendshipApply> listApply(Long userId) {
        QueryWrapper<ChFriendshipApply> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .or().eq("friend_id", userId);
        wrapper.orderByDesc("create_time");
        return applyDao.selectList(wrapper);
    }
}
