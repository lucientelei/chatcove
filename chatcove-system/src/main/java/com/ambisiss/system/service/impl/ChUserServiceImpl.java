package com.ambisiss.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.constant.RedisConstant;
import com.ambisiss.common.dto.ChUserDto;
import com.ambisiss.common.dto.ChUserInsertUpdateDto;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.common.utils.RedisUtil;
import com.ambisiss.common.utils.SnowflakeIdGenerator;
import com.ambisiss.common.vo.ChUserVo;
import com.ambisiss.system.entity.ChUser;
import com.ambisiss.system.mapper.ChUserDao;
import com.ambisiss.system.service.ChUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yitter.idgen.YitIdHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@Service
public class ChUserServiceImpl extends ServiceImpl<ChUserDao, ChUser> implements ChUserService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ChUserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int insertUser(ChUserInsertUpdateDto dto) {
        ChUser user = new ChUser();
        QueryWrapper<ChUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        ChUser chUser = userDao.selectOne(wrapper);
        if (chUser != null) {
            return -1;
        }
        BeanUtils.copyProperties(dto, user);
        //TODO 设置ID YitIdHelper
        user.setId(YitIdHelper.nextId());
        //设置密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodePassword);
        return userDao.insert(user);
    }

    @Override
    public int updateUser(ChUserInsertUpdateDto dto) {
        String token = request.getHeader(JwtUtils.header);
        //TODO 判断redis中是否还存在 不存在创建新token
        Long userId = JwtUtils.getClaim(token).get("userId").asLong();
        ChUser user = userDao.selectById(userId);
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

    @Override
    public ChUserVo userLogin(ChUserDto dto) {
        ChUserVo chUserVo = new ChUserVo();
        QueryWrapper<ChUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        ChUser user = userDao.selectOne(wrapper);
        if (StringUtils.isEmpty(user)) {
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (matches) {
            BeanUtils.copyProperties(user, chUserVo);
            String token = JwtUtils.createToken(String.valueOf(chUserVo.getId()), chUserVo.getUsername());
            chUserVo.setToken(token);
            //用户登录成功 生成token给到用户, 同时存储到redis中（key值为用户ID（标识）） value为生成的token
            redisUtil.setCacheObject(RedisConstant.USER_PREFIX + chUserVo.getId(), JSON.toJSONString(chUserVo));
            return chUserVo;
        }
        return null;
    }

    @Override
    public int userLogout(String token) {
        String redisToken = redisUtil.getCacheObject(RedisConstant.USER_TOKEN_PREFIX + token);
        if (StringUtils.isEmpty(redisToken)) {
            return 1;
        }
        redisUtil.deleteObject(RedisConstant.USER_TOKEN_PREFIX + token);
        return 1;
    }

    @Override
    public ChUserVo getUserByToken(String token) {
        String cacheObject = redisUtil.getCacheObject(RedisConstant.USER_TOKEN_PREFIX + token);
        return JSON.parseObject(cacheObject, ChUserVo.class);
    }
}
