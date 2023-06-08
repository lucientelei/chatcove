package com.ambisiss.system.service.impl;

import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.system.entity.ChUserFriend;
import com.ambisiss.system.mapper.ChUserFriendDao;
import com.ambisiss.system.service.ChUserFriendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2023-04-20
 */
@Service
public class ChUserFriendServiceImpl extends ServiceImpl<ChUserFriendDao, ChUserFriend> implements ChUserFriendService {

    @Autowired
    private ChUserFriendDao friendDao;

    @Override
    public int insertFriend(ChUserFriend dto) {
        return friendDao.insert(dto);
    }

    @Override
    public int delFriend(ChUserFriend dto) {
        QueryWrapper<ChUserFriend> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dto.getUserId()).eq("friend_id", dto.getFriendId());
        ChUserFriend userFriend = friendDao.selectOne(wrapper);
        if (userFriend == null) {
            return -1;
        }
        return friendDao.deleteById(userFriend.getId());
    }

    @Override
    public List<ChUserFriend> listFriend() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getClaimFiled(token, "userId");
        QueryWrapper<ChUserFriend> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return friendDao.selectList(wrapper);
    }
}
