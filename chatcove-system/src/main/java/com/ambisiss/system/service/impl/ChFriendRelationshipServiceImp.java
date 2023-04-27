package com.ambisiss.system.service.impl;

import com.ambisiss.common.bo.ChFriendRelationshipBo;
import com.ambisiss.common.constant.RelationEnum;
import com.ambisiss.common.dto.ChFriendRelationshipDto;
import com.ambisiss.system.entity.ChFriendRelationship;
import com.ambisiss.system.mapper.ChFriendRelationshipDao;
import com.ambisiss.system.service.ChFriendRelationshipService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ChFriendRelationshipServiceImp extends ServiceImpl<ChFriendRelationshipDao, ChFriendRelationship> implements ChFriendRelationshipService {

    @Autowired
    private ChFriendRelationshipDao friendRelationshipDao;

    @Override
    public int insertShip(ChFriendRelationshipDto dto) {
        ChFriendRelationship userFriendShip = new ChFriendRelationship();
        BeanUtils.copyProperties(dto, userFriendShip);
        //默认是未确认状态
        userFriendShip.setStatusId(RelationEnum.UNCONFIRMED.getId());
        //默认不是置顶状态
        userFriendShip.setIsTop(0);
        return friendRelationshipDao.insert(userFriendShip);
    }

    @Override
    public int delShip(Long id) {
        return friendRelationshipDao.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, Long statusId) {
        ChFriendRelationship relationship = friendRelationshipDao.selectById(id);
        relationship.setStatusId(statusId);
        return friendRelationshipDao.updateById(relationship);
    }

    @Override
    public int updateIsTop(Long id, Integer isTop) {
        ChFriendRelationship relationship = friendRelationshipDao.selectById(id);
        relationship.setIsTop(isTop);
        return friendRelationshipDao.updateById(relationship);
    }

    @Override
    public List<ChFriendRelationshipBo> listFriend(Long userId) {
        List<ChFriendRelationshipBo> result = new ArrayList<>();
        QueryWrapper<ChFriendRelationship> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status_id", RelationEnum.CONFIRMED.getId())
                .or().eq("status_id", RelationEnum.MASKED.getId())
                .or().eq("status_id", RelationEnum.BLACKLIST.getId());
        List<ChFriendRelationship> relationships = friendRelationshipDao.selectList(wrapper);
        //TODO 返回好友列表id信息 userservice查询好友信息返回
        for (ChFriendRelationship item : relationships) {
            ChFriendRelationshipBo bo = new ChFriendRelationshipBo();
            BeanUtils.copyProperties(item, bo);
            result.add(bo);
        }
        return result;
    }
}
