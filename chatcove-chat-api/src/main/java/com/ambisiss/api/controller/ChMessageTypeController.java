package com.ambisiss.api.controller;

import com.ambisiss.common.global.GlobalPage;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChMessageType;
import com.ambisiss.system.service.ChMessageTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.DeleteProvider;
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
@RequestMapping("/api/chat/chMessageType")
@Api(tags = "消息类型接口")
public class ChMessageTypeController {

    @Autowired
    private ChMessageTypeService messageTypeService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增消息类型")
    public GlobalResult insert(@RequestBody ChMessageType dto) {
        int result = messageTypeService.insertType(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除消息类型")
    public GlobalResult del(@PathVariable("id") Long id) {
        int result = messageTypeService.delType(id);
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新消息类型")
    public GlobalResult update(@RequestBody ChMessageType dto) {
        int result = messageTypeService.updateType(dto);
        return GlobalResult.success(result);
    }

    @PostMapping("/list")
    @ApiOperation(value = "分页查询消息类型")
    public GlobalResult list(@RequestBody ChMessageType dto,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        List<ChMessageType> result = messageTypeService.listPage(dto, pageNum, pageSize);
        return GlobalResult.success(GlobalPage.restPage(result));
    }
}
