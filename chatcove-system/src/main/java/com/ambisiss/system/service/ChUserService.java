package com.ambisiss.system.service;

import com.ambisiss.common.dto.ChUserDto;
import com.ambisiss.common.dto.ChUserInsertUpdateDto;
import com.ambisiss.common.vo.ChUserVo;
import com.ambisiss.system.entity.ChUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
public interface ChUserService extends IService<ChUser> {

    /**
     * 新增用户
     * @param dto
     * @return
     */
    int insertUser(ChUserInsertUpdateDto dto);

    /**
     * 更新用户信息
     * @param dto
     * @return
     */
    int updateUser(Long id, ChUserInsertUpdateDto dto);

    /**
     * 用户注销
     * @param id
     * @return
     */
    int delUser(Long id);

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    ChUserVo getUserById(Long id);

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    ChUserVo getUserByName(String username);

    /**
     * 用户登录
     * @param dto
     * @return
     */
    String userLogin(ChUserDto dto);
}
