package com.ambisiss.api.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChUserFriend;
import com.ambisiss.system.service.ChUserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/api/chat/chUserFriend")
@Api(tags = "用户好友接口")
public class ChUserFriendController {

    @Autowired
    private ChUserFriendService friendService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增用户好友关系")
    public GlobalResult insertUserFriend(@RequestBody ChUserFriend dto) {
        int result = friendService.insertFriend(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除用户好友关系")
    public GlobalResult delUserFriend(@RequestBody ChUserFriend dto) {
        int result = friendService.delFriend(dto);

        return GlobalResult.success(result);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询用户的好友关系")
    public GlobalResult listUserFriend() {
        List<ChUserFriend> result = friendService.listFriend();
        return GlobalResult.success(result);
    }
}
