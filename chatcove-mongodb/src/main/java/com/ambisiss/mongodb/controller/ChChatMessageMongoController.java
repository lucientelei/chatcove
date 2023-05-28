package com.ambisiss.mongodb.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.mongodb.dto.ChChatMsgInsertDto;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-11 20:10:43
 */
@RestController
@RequestMapping("/api/chat/mongodb/chChatMessage")
@Api(tags = "mongodb用户聊天接口", description = "ChChatMessageMongoDbController")
public class ChChatMessageMongoController {

    @Autowired
    private ChChatMessageMongoService messageMongoService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增消息缓存")
    public GlobalResult insertMsg(@RequestBody ChChatMsgInsertDto dto) {
        String result = messageMongoService.insertMessage(dto);
        return GlobalResult.success(result);
    }

    @GetMapping("/listAll")
    @ApiOperation(value = "获取全部数据")
    public GlobalResult listAll(@RequestParam Long userId) {
        ChChatMessageMongo result = messageMongoService.listAll(userId);
        return GlobalResult.success(result);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新已读状态")
    public GlobalResult updateMsg(@RequestParam Long userId,
                                  @RequestParam String messageUuid,
                                  @RequestParam int isRead) {
        int result = messageMongoService.updateRead(userId, messageUuid, isRead);
        return GlobalResult.success(result);
    }

    @GetMapping("/unread")
    @ApiOperation(value = "获取用户未读消息")
    public GlobalResult listUnRead(@RequestParam Long userId) {
        ChChatMessageMongo result = messageMongoService.listUnReadByUserId(userId);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除消息缓存")
    public GlobalResult deleteMessage(@RequestParam Long userId,
                                      @RequestParam String messageUuid) {
        int result = messageMongoService.delMessage(userId, messageUuid);
        return GlobalResult.success(result);
    }
}
