package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChChatRecordsDto;
import com.ambisiss.common.dto.ChChatRecordsPageDto;
import com.ambisiss.common.global.GlobalPage;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChChatRecords;
import com.ambisiss.system.service.ChChatRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/chat/chChatRecords")
@Api(tags = "用户聊天记录接口")
public class ChChatRecordsController {

    @Autowired
    private ChChatRecordsService chChatRecordsService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增聊天记录")
    public GlobalResult insertRecords(@RequestBody @Validated ChChatRecordsDto dto) {
        int result = chChatRecordsService.insertRecords(dto);
        if (result == 0) {
            return GlobalResult.error("添加失败");
        }
        return GlobalResult.success();
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除聊天记录")
    public GlobalResult delRecords(@RequestBody List<Long> ids) {
        int result = chChatRecordsService.delRecords(ids);
        if (result == 0) {
            return GlobalResult.error("删除失败");
        }
        return GlobalResult.success();
    }

    @GetMapping("/list/page")
    @ApiOperation(value = "分页获取聊天记录")
    public GlobalResult getPageRecords(@RequestBody @Validated ChChatRecordsPageDto dto) {
        List<ChChatRecords> result = chChatRecordsService.listRecords(dto);
        return GlobalResult.success(GlobalPage.restPage(result));
    }
}
