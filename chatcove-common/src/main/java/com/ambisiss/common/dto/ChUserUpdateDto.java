package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 21:25:01
 */
@Getter
@Setter
@ApiModel(value = "ChUserUpdateDto对象", description = "用户信息更新传输对象")
public class ChUserUpdateDto {

    private Long id;

    @Length(min = 6, max = 20, message = "用户名长度在6-20个字符之间")
    private String username;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$", message = "至少包含字母、数字，且长度6-20位")
    private String password;

    private String gender;

    private String avatar;

    @Pattern(regexp = "/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$/", message = "请输入正确的电话号码")
    private String phone;

    @Email(message = "请输入正确的邮箱地址")
    private String email;

    private String signature;
}
