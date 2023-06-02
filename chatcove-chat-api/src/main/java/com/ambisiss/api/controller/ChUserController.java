package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChUserDto;
import com.ambisiss.common.dto.ChUserInsertUpdateDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.vo.ChUserVo;
import com.ambisiss.system.service.ChUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/api/chat/chUser")
@Api(tags = "用户接口")
public class ChUserController {

    @Autowired
    private ChUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "-1：用户名重名")
    public GlobalResult registerUser(@RequestBody @Validated ChUserInsertUpdateDto dto) {
        int result = userService.insertUser(dto);
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息")
    public GlobalResult updateUserInfo(@RequestBody @Validated ChUserInsertUpdateDto dto) {
        int result = userService.updateUser(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除用户")
    public GlobalResult delUser(@RequestParam Long id) {
        int result = userService.delUser(id);
        return GlobalResult.success(result);
    }

    @GetMapping("/info/id")
    @ApiOperation(value = "根据ID查找用户信息")
    public GlobalResult getUserById(@RequestParam Long id) {
        ChUserVo result = userService.getUserById(id);
        return GlobalResult.success(result);
    }

    @GetMapping("/info/name")
    @ApiOperation(value = "根据用户名查找用户信息")
    public GlobalResult getUserByUsername(@RequestParam String username) {
        ChUserVo result = userService.getUserByName(username);
        return GlobalResult.success(result);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "null:用户名或密码错误")
    public GlobalResult userLogin(@RequestBody ChUserDto dto) {
        ChUserVo result = userService.userLogin(dto);
        return GlobalResult.success(result);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    public GlobalResult userLogout(@RequestParam String token) {
        int result = userService.userLogout(token);
        return GlobalResult.success(result);
    }

    @GetMapping("/info/token")
    @ApiOperation(value = "根据TOKEN查找用户信息")
    public GlobalResult getUserByToken(@RequestParam String token) {
        ChUserVo result = userService.getUserByToken(token);
        return GlobalResult.success(result);
    }
}
