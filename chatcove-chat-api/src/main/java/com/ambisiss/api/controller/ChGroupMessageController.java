package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChGroupMessageDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.vo.ChGroupMessageVo;
import com.ambisiss.system.service.ChGroupMessageService;
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
@RequestMapping("/api/chat/chGroupMessage")
@Api(tags = "群组信息接口", description = "ChGroupMessageController")
public class ChGroupMessageController {

    @Autowired
    private ChGroupMessageService groupMessageService;

    @PostMapping("/insertMessage")
    @ApiOperation(value = "新增群组消息记录")
    public GlobalResult insertMessage(@RequestBody ChGroupMessageDto dto) {
        int result = groupMessageService.insertMessage(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/delMessage")
    @ApiOperation(value = "删除群组消息记录")
    public GlobalResult delMessage(@RequestParam Long id) {
        int result = groupMessageService.delMessage(id);
        return GlobalResult.success(result);
    }

    @GetMapping("/list/message")
    @ApiOperation(value = "获取群组聊天记录")
    public GlobalResult listMessage(@RequestParam Long groupId) {
        List<ChGroupMessageVo> result = groupMessageService.listMessage(groupId);
        return GlobalResult.success(result);
    }

}
