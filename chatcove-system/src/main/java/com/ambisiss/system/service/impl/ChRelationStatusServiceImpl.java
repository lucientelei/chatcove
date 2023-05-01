package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChRelationStatusDto;
import com.ambisiss.system.entity.ChRelationStatus;
import com.ambisiss.system.mapper.ChRelationStatusDao;
import com.ambisiss.system.service.ChRelationStatusService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class ChRelationStatusServiceImpl extends ServiceImpl<ChRelationStatusDao, ChRelationStatus> implements ChRelationStatusService {

    @Autowired
    private ChRelationStatusDao relationStatusDao;

    @Override
    public int insertStatus(ChRelationStatusDto dto) {
        ChRelationStatus relationStatus = new ChRelationStatus();
        BeanUtils.copyProperties(dto, relationStatus);
        return relationStatusDao.insert(relationStatus);
    }

    @Override
    public int delStatus(Long id) {
        return relationStatusDao.deleteById(id);
    }

    @Override
    public int updateStatus(Long id, ChRelationStatusDto dto) {
        ChRelationStatus relationStatus = relationStatusDao.selectById(id);
        if (!StringUtils.isEmpty(dto.getName())) {
            relationStatus.setName(dto.getName());
        }
        if (!StringUtils.isEmpty(dto.getDescription())) {
            relationStatus.setName(dto.getDescription());
        }
        return relationStatusDao.updateById(relationStatus);
    }

    @Override
    public List<ChRelationStatus> listPage(ChRelationStatusDto dto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ChRelationStatus> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        if (!StringUtils.isEmpty(dto.getName())) {
            wrapper.like("name", dto.getName());
        }
        if (!StringUtils.isEmpty(dto.getDescription())) {
            wrapper.like("description", dto.getDescription());
        }
        return relationStatusDao.selectList(wrapper);
    }
}
