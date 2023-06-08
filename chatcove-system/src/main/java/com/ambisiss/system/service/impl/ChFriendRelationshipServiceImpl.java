package com.ambisiss.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.bo.ChFriendRelationshipBo;
import com.ambisiss.common.enums.RelationEnum;
import com.ambisiss.common.dto.ChFriendRelationshipDto;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.common.utils.StringUtils;
import com.ambisiss.system.entity.ChFriendRelationship;
import com.ambisiss.system.mapper.ChFriendRelationshipDao;
import com.ambisiss.system.service.ChFriendRelationshipService;
import com.ambisiss.system.service.ChUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
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
public class ChFriendRelationshipServiceImpl extends ServiceImpl<ChFriendRelationshipDao, ChFriendRelationship> implements ChFriendRelationshipService {

    @Autowired
    private ChFriendRelationshipDao friendRelationshipDao;

    @Autowired
    private ChUserService userService;

    @Override
    public int insertShip(ChFriendRelationshipDto dto) {
        ChFriendRelationship userFriendShip = new ChFriendRelationship();
        BeanUtils.copyProperties(dto, userFriendShip);
        //默认是未确认状态
        userFriendShip.setStatusId(dto.getStatusId());
        //默认不是置顶状态
        userFriendShip.setIsTop(0);
        //设置好友备注
        userFriendShip.setFriendNickname(userService.getUserById(dto.getFriendId()).getUsername());
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
    public List<ChFriendRelationshipBo> listFriend() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getClaimFiled(token, "userId");
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
            bo.setUserId(item.getFriendId());
            bo.setNickName(item.getFriendNickname());
            result.add(bo);
        }
        return result;
    }
}
