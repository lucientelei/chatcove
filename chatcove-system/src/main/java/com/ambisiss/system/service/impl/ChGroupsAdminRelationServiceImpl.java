package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChGroupsAdminRelationDto;
import com.ambisiss.system.entity.ChGroupsAdminRelation;
import com.ambisiss.system.mapper.ChGroupsAdminRelationDao;
import com.ambisiss.system.service.ChGroupsAdminRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-27
 */
@Service
public class ChGroupsAdminRelationServiceImpl extends ServiceImpl<ChGroupsAdminRelationDao, ChGroupsAdminRelation> implements ChGroupsAdminRelationService {

    @Autowired
    private ChGroupsAdminRelationDao relationDao;

    @Override
    public int insertRelation(ChGroupsAdminRelationDto dto) {
        ChGroupsAdminRelation relation = new ChGroupsAdminRelation();
        BeanUtils.copyProperties(dto, relation);
        return relationDao.insert(relation);
    }

    @Override
    public int delRelation(ChGroupsAdminRelationDto dto) {
        QueryWrapper<ChGroupsAdminRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", dto.getGroupId()).eq("user_id", dto.getUserId());
        ChGroupsAdminRelation adminRelation = relationDao.selectById(wrapper);
        return relationDao.deleteById(adminRelation);
    }

    @Override
    public List<Long> getGroupAdmin(Long groupId) {
        QueryWrapper<ChGroupsAdminRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id", groupId);
        List<ChGroupsAdminRelation> adminRelations = relationDao.selectList(wrapper);
        return adminRelations.stream().map(ChGroupsAdminRelation::getUserId).collect(Collectors.toList());
    }
}
