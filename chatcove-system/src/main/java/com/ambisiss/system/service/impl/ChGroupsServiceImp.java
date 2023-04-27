package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChGroupsInsertDto;
import com.ambisiss.system.entity.ChGroups;
import com.ambisiss.system.mapper.ChGroupsDao;
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
public class ChGroupsServiceImp extends ServiceImpl<ChGroupsDao, ChGroups> implements ChGroupsService {

    @Autowired
    private ChGroupsDao groupsDao;

    @Override
    public int insertGroup(ChGroupsInsertDto dto) {
        ChGroups groups = new ChGroups();
        groups.setName(dto.getName());
        groupsDao.insert(groups);
        Long groupsId = groups.getId();
        //TODO 添加admin_relation关系

        //TODO 添加group_member
        return 0;
    }

    @Override
    public int delGroup(Long id) {
        //TODO 删除admin_relation表中管理员

        //TODO 删除group_member表中的成员

        //TODO 删除群组ID
        return groupsDao.deleteById(id);
    }
}
