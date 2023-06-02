package com.ambisiss.common.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 21:37:53
 */
@Getter
@Setter
@ApiModel(value = "ChUserVo对象", description = "用户信息显示传输对象")
public class ChUserVo {

    private Long id;

    private String username;

    private String gender;

    private String avatar;

    private String phone;

    private String email;

    private String signature;

    private String token;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
}
