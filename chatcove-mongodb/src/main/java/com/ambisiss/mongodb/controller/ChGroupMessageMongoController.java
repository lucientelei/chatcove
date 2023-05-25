package com.ambisiss.mongodb.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.mongodb.dto.ChGroupMsgInsertDto;
import com.ambisiss.mongodb.entity.ChGroupMessageMongo;
import com.ambisiss.mongodb.service.ChGroupMessageMongoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 17:05:23
 */
@RestController
@RequestMapping("/api/chat/mongodb/chGroupMessage")
@Api(tags = "mongodb群聊天接口", description = "ChGroupMessageMongoController")
public class ChGroupMessageMongoController {

    @Autowired
    private ChGroupMessageMongoService messageMongoService;

    @PostMapping("/save")
    @ApiOperation(value = "新增群聊消息")
    public GlobalResult insert(@RequestBody ChGroupMsgInsertDto dto) {
        int result = messageMongoService.insertGroupMsg(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除群聊消息")
    public GlobalResult deleteGroupMsg(@RequestParam Long userId,
                                       @RequestParam String messageUuid) {
        int result = messageMongoService.delGroupMsg(userId, messageUuid);
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新已读状态")
    public GlobalResult updateReadStatus(@RequestParam Long userId,
                                         @RequestParam String messageUuid,
                                         @RequestParam int isRead) {
        int result = messageMongoService.updateRead(userId, messageUuid, isRead);
        return GlobalResult.success(result);
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询全部群聊消息")
    public GlobalResult listAll(@RequestParam Long userId) {
        ChGroupMessageMongo result = messageMongoService.listAll(userId);
        return GlobalResult.success(result);
    }

    @GetMapping("/listById")
    @ApiOperation(value = "查询用户未读消息")
    public GlobalResult listByUserId(@RequestParam Long userId) {
        ChGroupMessageMongo result = messageMongoService.listUnReadByUserId(userId);
        return GlobalResult.success(result);
    }

}
