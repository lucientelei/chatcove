package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChGroupMembersDto;
import com.ambisiss.common.vo.ChGroupMembersVo;
import com.ambisiss.system.entity.ChGroupMembers;
import com.ambisiss.system.entity.ChUser;
import com.ambisiss.system.mapper.ChGroupMembersDao;
import com.ambisiss.system.mapper.ChUserDao;
import com.ambisiss.system.service.ChGroupMembersService;
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
 * @since 2023-04-20
 */
@Service
public class ChGroupMembersServiceImp extends ServiceImpl<ChGroupMembersDao, ChGroupMembers> implements ChGroupMembersService {

    @Autowired
    private ChGroupMembersDao groupMembersDao;

    @Autowired
    private ChUserDao userDao;

    @Override
    public int insertMember(ChGroupMembersDto dto) {
        ChGroupMembers members = new ChGroupMembers();
        BeanUtils.copyProperties(dto, members);
        members.setGroupNickname(userDao.selectById(dto.getMemberId()).getUsername());
        return groupMembersDao.insert(members);
    }

    @Override
    public int delMember(ChGroupMembersDto dto) {
        QueryWrapper<ChGroupMembers> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", dto.getGroupId()).eq("member_id", dto.getMemberId());
        return groupMembersDao.delete(wrapper);
    }

    @Override
    public List<ChGroupMembersVo> listMember(Long groupId) {
        QueryWrapper<ChGroupMembers> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        List<ChGroupMembers> membersList = groupMembersDao.selectList(wrapper);
        return generatorVo(membersList);
    }

    private List<ChGroupMembersVo> generatorVo(List<ChGroupMembers> membersList) {
        List<ChGroupMembersVo> membersVos = new ArrayList<>();
        for (ChGroupMembers item : membersList) {
            ChGroupMembersVo membersVo = new ChGroupMembersVo();
            membersVo.setMemberId(item.getMemberId());
            membersVo.setGroupId(item.getGroupId());
            membersVo.setGroupNickname(item.getGroupNickname());
            ChUser chUser = userDao.selectById(item.getMemberId());
            membersVo.setUsername(chUser.getUsername());
            membersVo.setGender(chUser.getGender());
            membersVo.setAvatar(chUser.getAvatar());
            membersVos.add(membersVo);
        }

        return membersVos;
    }
}
