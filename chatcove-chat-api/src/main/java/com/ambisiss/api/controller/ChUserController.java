package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChUserDto;
import com.ambisiss.common.dto.ChUserUpdateDto;
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
 *  前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/api/chat/chUser")
@Api(tags = "用户管理接口")
public class ChUserController {

    @Autowired
    private ChUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "新增用户")
    public GlobalResult insert(@RequestBody @Validated ChUserDto dto){
        int result = userService.insertUser(dto);
        if (result == -1) {
            GlobalResult.error("用户名已存在");
        }
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息")
    public GlobalResult update(@RequestBody @Validated ChUserUpdateDto dto){
        int result = userService.updateUser(dto);
        if (result == 0) {
            GlobalResult.error("更新失败");
        }
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除用户")
    public GlobalResult del(@PathVariable("id") Long id){
        int result = userService.delUser(id);
        if (result == 0) {
            GlobalResult.error("删除失败");
        }
        return GlobalResult.success(result);
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "根据ID查找用户信息")
    public GlobalResult getById(@PathVariable("id") Long id){
        ChUserVo result = userService.getUserById(id);
        if (result == null) {
            GlobalResult.error("不存在该用户");
        }
        return GlobalResult.success(result);
    }

    @GetMapping("/info")
    @ApiOperation(value = "根据ID查找用户信息")
    public GlobalResult getByUsername(@RequestParam String username){
        ChUserVo result = userService.getUserByName(username);
        if (result == null) {
            GlobalResult.error("不存在该用户");
        }
        return GlobalResult.success(result);
    }
}
