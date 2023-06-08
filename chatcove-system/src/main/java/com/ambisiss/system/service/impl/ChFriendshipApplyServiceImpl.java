package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChFriendRelationshipDto;
import com.ambisiss.common.enums.RelationEnum;
import com.ambisiss.common.dto.ChFriendshipApplyDto;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.system.entity.ChFriendshipApply;
import com.ambisiss.system.mapper.ChFriendshipApplyDao;
import com.ambisiss.system.service.ChFriendRelationshipService;
import com.ambisiss.system.service.ChFriendshipApplyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest request;

    @Override
    public int insertApply(ChFriendshipApplyDto dto) {
        ChFriendshipApply apply = new ChFriendshipApply();
        BeanUtils.copyProperties(dto, apply);
        apply.setStatusId(RelationEnum.UNCONFIRMED.getId());
        //TODO 陌生人发送申请后添加friendRelationship 双向关系 statusId为1
//        insertFriendRelation(apply);
        return applyDao.insert(apply);
    }

    @Override
    public int delApply(Long id) {
        return applyDao.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, Long statusId) {
        ChFriendshipApply apply = applyDao.selectById(id);
        apply.setStatusId(statusId);
        //TODO 更新friendRelationShip 双向关系的status
        insertFriendRelation(apply);
        return applyDao.updateById(apply);
    }

    @Override
    public List<ChFriendshipApply> listApply() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getClaimFiled(token, "userId");
        QueryWrapper<ChFriendshipApply> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .or().eq("friend_id", userId);
        wrapper.orderByDesc("create_time");
        return applyDao.selectList(wrapper);
    }

    /**
     * 插入friendRelationShip双向关系
     *
     * @param apply
     */
    private void insertFriendRelation(ChFriendshipApply apply) {
        ChFriendRelationshipDto userFriendDto = new ChFriendRelationshipDto();
        userFriendDto.setUserId(apply.getUserId());
        userFriendDto.setFriendId(apply.getFriendId());
        userFriendDto.setStatusId(apply.getStatusId());
        friendRelationshipService.insertShip(userFriendDto);
        ChFriendRelationshipDto friendUserDto = new ChFriendRelationshipDto();
        friendUserDto.setUserId(apply.getFriendId());
        friendUserDto.setFriendId(apply.getUserId());
        friendUserDto.setStatusId(apply.getStatusId());
        friendRelationshipService.insertShip(friendUserDto);
    }
}
