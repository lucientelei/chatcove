package com.ambisiss.api.controller.mongodb;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.mongodb.entity.ChChatMessage;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-11 20:10:43
 */
@RestController
@RequestMapping("/api/chat/mongodb/chChatMessage")
@Api(tags = "mongodb用户聊天接口", description = "ChChatMessageMongoDbController")
public class ChChatMessageMongoDbController {

    @Autowired
    private ChChatMessageMongoService messageMongoService;

    @GetMapping("/listAll")
    @ApiOperation(value = "获取全部数据")
    public GlobalResult listAll(){
        List<ChChatMessage> result = messageMongoService.listAll();
        return GlobalResult.success(result);
    }

}
