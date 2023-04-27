package com.ambisiss.api.controller;

import com.ambisiss.common.global.GlobalPage;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChMessageStatus;
import com.ambisiss.system.service.ChMessageStatusService;
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
@RequestMapping("/api/chat/chMessageStatus")
@Api(tags = "消息状态接口")
public class ChMessageStatusController {

    @Autowired
    private ChMessageStatusService statusService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增消息状态")
    public GlobalResult insertMessageStatus(@RequestBody ChMessageStatus dto) {
        int result = statusService.insertStatus(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除消息状态")
    public GlobalResult delMessageStatus(@RequestParam Long id) {
        int result = statusService.delStatus(id);
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新消息状态")
    public GlobalResult updateMessageStatus(@RequestBody ChMessageStatus dto) {
        int result = statusService.updateStatus(dto);
        return GlobalResult.success(result);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询消息状态")
    public GlobalResult listPageMessageStatus(@RequestBody ChMessageStatus dto,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        List<ChMessageStatus> result = statusService.listPage(dto, pageNum, pageSize);
        return GlobalResult.success(GlobalPage.restPage(result));
    }

}
