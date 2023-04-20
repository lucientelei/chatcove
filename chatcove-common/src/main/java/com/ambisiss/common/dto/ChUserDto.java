package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 21:25:01
 */
@Getter
@Setter
@ApiModel(value = "ChUserDto对象", description = "用户注册 登录传输对象")
public class ChUserDto {

    @NotBlank
    @Length(min = 6, max = 20, message = "用户名长度在6-20个字符之间")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$", message = "至少包含字母、数字，且长度6-20位")
    private String password;
}
