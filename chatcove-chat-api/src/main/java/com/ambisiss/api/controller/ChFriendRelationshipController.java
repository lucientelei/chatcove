package com.ambisiss.api.controller;

import com.ambisiss.common.aspect.AccessLimit;
import com.ambisiss.common.bo.ChFriendRelationshipBo;
import com.ambisiss.common.dto.ChFriendRelationshipDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChFriendRelationship;
import com.ambisiss.system.service.ChFriendRelationshipService;
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
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/api/chat/chFriendRelationship")
@Api(tags = "好友关系接口")
public class ChFriendRelationshipController {

    @Autowired
    private ChFriendRelationshipService relationshipService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增好友关系")
    public GlobalResult insertFriendRelationship(@RequestBody ChFriendRelationshipDto dto) {
        int result = relationshipService.insertShip(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除好友关系")
    public GlobalResult delFriendRelationship(@RequestParam Long id) {
        int result = relationshipService.delShip(id);
        return GlobalResult.success(result);
    }

    @PostMapping("/update/status")
    @ApiOperation(value = "更新好友关系状态")
    public GlobalResult updateFriendRelationshipStatus(@RequestParam Long id,
                                     @RequestParam Long statusId) {
        int result = relationshipService.updateStatus(id, statusId);
        return GlobalResult.success(result);
    }

    @PostMapping("/update/istop")
    @ApiOperation(value = "更新好友置顶状态")
    public GlobalResult updateFriendRelationshipIsTop(@RequestParam Long id,
                                    @RequestParam Integer isTop) {
        int result = relationshipService.updateIsTop(id, isTop);
        return GlobalResult.success(result);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取用户好友列表")
    public GlobalResult listFriendRelationship() {
        List<ChFriendRelationshipBo> result = relationshipService.listFriend();
        return GlobalResult.success(result);
    }
}
