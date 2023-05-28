package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChGroupMembersDto;
import com.ambisiss.common.dto.ChGroupsAdminRelationDto;
import com.ambisiss.common.dto.ChGroupsInsertDto;
import com.ambisiss.system.entity.ChGroups;
import com.ambisiss.system.mapper.ChGroupsDao;
import com.ambisiss.system.service.ChGroupMembersService;
import com.ambisiss.system.service.ChGroupsAdminRelationService;
import com.ambisiss.system.service.ChGroupsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@Service
public class ChGroupsServiceImpl extends ServiceImpl<ChGroupsDao, ChGroups> implements ChGroupsService {

    @Autowired
    private ChGroupsDao groupsDao;

    @Autowired
    private ChGroupsAdminRelationService adminRelationService;

    @Autowired
    private ChGroupMembersService membersService;

    @Override
    public int insertGroup(ChGroupsInsertDto dto) {
        ChGroups groups = new ChGroups();
        groups.setName(dto.getName());
        //TODO 添加group
        int result = groupsDao.insert(groups);
        Long groupsId = groups.getId();
        //TODO 添加admin_relation关系
        dto.getAdminList().forEach(item -> {
            ChGroupsAdminRelationDto relationDto = new ChGroupsAdminRelationDto();
            relationDto.setGroupId(groupsId);
            relationDto.setUserId(item);
            adminRelationService.insertRelation(relationDto);
        });
        //TODO 添加group_member
        dto.getMemberList().forEach(item -> {
            ChGroupMembersDto membersDto = new ChGroupMembersDto();
            membersDto.setGroupId(groupsId);
            membersDto.setMemberId(item);
            membersService.insertMember(membersDto);
        });
        return result;
    }

    @Override
    public int delGroup(Long groupId) {
        //TODO 删除admin_relation表中管理员
        adminRelationService.delGroupRelation(groupId);
        //TODO 删除group_member表中的成员
        membersService.delGroupMember(groupId);
        //TODO 删除群组ID
        return groupsDao.deleteById(groupId);
    }
}
