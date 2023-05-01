package com.ambisiss.system.service.impl;

import com.ambisiss.system.entity.ChMessageStatus;
import com.ambisiss.system.mapper.ChMessageStatusDao;
import com.ambisiss.system.service.ChMessageStatusService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
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
public class ChMessageStatusServiceImpl extends ServiceImpl<ChMessageStatusDao, ChMessageStatus> implements ChMessageStatusService {

    @Autowired
    private ChMessageStatusDao typeDao;

    @Override
    public int insertStatus(ChMessageStatus dto) {
        return typeDao.insert(dto);
    }

    @Override
    public int delStatus(Long id) {
        return typeDao.deleteById(id);
    }

    @Override
    public int updateStatus(ChMessageStatus dto) {
        return typeDao.updateById(dto);
    }

    @Override
    public List<ChMessageStatus> listPage(ChMessageStatus dto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<ChMessageStatus> wrapper = new QueryWrapper<>();
        wrapper.eq(dto.getName() != null, "name", dto.getName());
        wrapper.eq(dto.getDescription() != null, "description", dto.getDescription());
        return typeDao.selectList(wrapper);
    }
}
