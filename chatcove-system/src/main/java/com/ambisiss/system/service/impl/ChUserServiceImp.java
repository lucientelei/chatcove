package com.ambisiss.system.service.impl;

import com.ambisiss.common.dto.ChUserDto;
import com.ambisiss.common.dto.ChUserUpdateDto;
import com.ambisiss.common.utils.SnowflakeIdGenerator;
import com.ambisiss.common.vo.ChUserVo;
import com.ambisiss.system.entity.ChUser;
import com.ambisiss.system.mapper.ChUserDao;
import com.ambisiss.system.service.ChUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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
public class ChUserServiceImp extends ServiceImpl<ChUserDao, ChUser> implements ChUserService {

    @Autowired
    private ChUserDao userDao;

    @Override
    public int insertUser(ChUserDto dto) {
        ChUser user = new ChUser();
        QueryWrapper<ChUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        ChUser chUser = userDao.selectOne(wrapper);
        if (chUser != null) {
            return -1;
        }
        BeanUtils.copyProperties(dto, user);
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(0L, 0L);
        user.setId(generator.generateNextId());
        return userDao.insert(user);
    }

    @Override
    public int updateUser(ChUserUpdateDto dto) {
        ChUser user = userDao.selectById(dto.getId());
        BeanUtils.copyProperties(dto, user);
        return userDao.updateById(user);
    }

    @Override
    public int delUser(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public ChUserVo getUserById(Long id) {
        ChUser user = userDao.selectById(id);
        ChUserVo userVo = new ChUserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public ChUserVo getUserByName(String username) {
        QueryWrapper<ChUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        ChUser user = userDao.selectOne(wrapper);
        ChUserVo userVo = new ChUserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}
