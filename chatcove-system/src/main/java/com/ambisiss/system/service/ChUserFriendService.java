package com.ambisiss.system.service;

import com.ambisiss.system.entity.ChUserFriend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
public interface ChUserFriendService extends IService<ChUserFriend> {

    /**
     * 添加
     * @param dto
     * @return
     */
    int insertFriend(ChUserFriend dto);

    /**
     * 删除好友关系 单方面
     * @param dto
     * @return
     */
    int delFriend(ChUserFriend dto);

    /**
     * 查询用户好友关系
     * @return
     */
    List<ChUserFriend> listFriend();
}
