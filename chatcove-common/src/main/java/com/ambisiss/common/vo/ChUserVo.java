package com.ambisiss.common.vo;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 21:37:53
 */
@Getter
@Setter
@ApiModel(value = "ChUserVo对象", description = "用户信息显示传输对象")
public class ChUserVo  {

    private Long id;

    private String username;

    private String gender;

    private String avatar;

    private String phone;

    private String email;

    private String signature;

}
