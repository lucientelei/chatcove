package com.ambisiss.system.mapper;

import com.ambisiss.system.entity.ChUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@Mapper
public interface ChUserDao extends BaseMapper<ChUser> {

    /**
     * 通过用户名获取用户ID
     * @param username
     * @return
     */
    Long getIdByUsername(String username);
}
