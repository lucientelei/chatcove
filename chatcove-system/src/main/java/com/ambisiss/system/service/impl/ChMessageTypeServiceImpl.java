package com.ambisiss.system.service.impl;

import com.ambisiss.system.entity.ChMessageType;
import com.ambisiss.system.mapper.ChMessageTypeDao;
import com.ambisiss.system.service.ChMessageTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
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
 * @since 2023-04-20
 */
@Service
public class ChMessageTypeServiceImpl extends ServiceImpl<ChMessageTypeDao, ChMessageType> implements ChMessageTypeService {

    @Autowired
    private ChMessageTypeDao messageTypeDao;

    @Override
    public int insertType(ChMessageType dto) {
        return messageTypeDao.insert(dto);
    }

    @Override
    public int delType(Long id) {
        return messageTypeDao.deleteById(id);
    }

    @Override
    public int updateType(ChMessageType dto) {
        ChMessageType type = new ChMessageType();
        BeanUtils.copyProperties(dto, type);
        return messageTypeDao.updateById(type);
    }

    @Override
    public List<ChMessageType> listPage(ChMessageType dto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ChMessageType> wrapper = new QueryWrapper<>();
        wrapper.like(dto.getName() != null, "name", dto.getName());
        wrapper.like(dto.getDescription() != null, "description", dto.getDescription());
        return messageTypeDao.selectList(wrapper);
    }
}
