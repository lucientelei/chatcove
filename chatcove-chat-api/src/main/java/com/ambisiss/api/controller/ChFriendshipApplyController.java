package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChFriendshipApplyDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChFriendshipApply;
import com.ambisiss.system.service.ChFriendshipApplyService;
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
@RequestMapping("/api/chat/chFriendshipApply")
@Api(tags = "好友申请接口")
public class ChFriendshipApplyController {

    @Autowired
    private ChFriendshipApplyService applyService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增申请")
    public GlobalResult insertFriendshipApply(@RequestBody ChFriendshipApplyDto dto) {
        int result = applyService.insertApply(dto);
        if (result == 0) {
            return GlobalResult.error();
        }
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除申请")
    public GlobalResult delFriendshipApply(@PathVariable("id") Long id) {
        int result = applyService.delApply(id);
        if (result == 0) {
            return GlobalResult.error();
        }
        return GlobalResult.success(result);
    }

    @PostMapping("/update/status/{id}")
    @ApiOperation(value = "更新申请状态")
    public GlobalResult updateFriendshipApply(@PathVariable("id") Long id,
                               @RequestParam Long statusId) {
        int result = applyService.updateStatus(id, statusId);
        if (result == 0) {
            return GlobalResult.error();
        }
        return GlobalResult.success(result);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查看申请列表")
    public GlobalResult listFriendshipApply(@RequestParam Long userId) {
        List<ChFriendshipApply> result = applyService.listApply(userId);
        return GlobalResult.success(result);
    }
}
