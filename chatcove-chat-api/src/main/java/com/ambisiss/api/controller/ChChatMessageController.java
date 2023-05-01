package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChChatMessageDto;
import com.ambisiss.common.dto.ChChatMessagePageDto;
import com.ambisiss.common.global.GlobalPage;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.entity.ChChatMessage;
import com.ambisiss.system.service.ChChatMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/api/chat/chChatMessage")
@Api(tags = "用户聊天接口", description = "ChChatMessageController")
public class ChChatMessageController {

    @Autowired
    private ChChatMessageService messageService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增聊天记录")
    public GlobalResult insertRecords(@RequestBody @Validated ChChatMessageDto dto) {
        int result = messageService.insertMessage(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除聊天记录")
    public GlobalResult delRecords(@RequestBody List<Long> ids) {
        int result = messageService.delMessage(ids);
        return GlobalResult.success(result);
    }

    @GetMapping("/list/page")
    @ApiOperation(value = "分页获取聊天记录")
    public GlobalResult getPageRecords(@RequestBody @Validated ChChatMessagePageDto dto,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        List<ChChatMessage> result = messageService.listMessage(dto, pageNum, pageSize);
        return GlobalResult.success(GlobalPage.restPage(result));
    }

}
