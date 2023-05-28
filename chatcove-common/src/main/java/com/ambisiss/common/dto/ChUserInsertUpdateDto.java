package com.ambisiss.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 21:25:01
 */
@Getter
@Setter
@ApiModel(value = "ChUserUpdateDto对象", description = "用户信息更新传输对象")
public class ChUserInsertUpdateDto {

    @NotBlank(message = "用户名不能为空", groups = Insert.class)
    @Pattern(regexp = "[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9\\u4E00-\\u9FA5]{1,20}", message = "用户名长度在6-20个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空", groups = Insert.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{6,20}$", message = "密码至少包含字母、数字，且长度6-20位")
    private String password;

    private String gender;

    private String avatar;

    @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    private String phone;

    @Email(message = "请输入正确的邮箱地址")
    private String email;

    private String signature;
}
