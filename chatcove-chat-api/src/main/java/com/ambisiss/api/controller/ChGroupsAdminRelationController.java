package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChGroupsAdminRelationDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.service.ChGroupsAdminRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-27
 */
@RestController
@RequestMapping("/api/chat/chGroupsAdminRelation")
@Api(tags = "ChGroupsAdminRelationController", description = "群组管理员接口")
public class ChGroupsAdminRelationController {

    @Autowired
    private ChGroupsAdminRelationService relationService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增群组管理员关系")
    public GlobalResult insertRelation(@RequestBody ChGroupsAdminRelationDto dto){
        int result = relationService.insertRelation(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/del")
    @ApiOperation(value = "删除群组管理员关系")
    public GlobalResult delRelation(@RequestBody ChGroupsAdminRelationDto dto){
        int result = relationService.delRelation(dto);
        return GlobalResult.success(result);
    }

    @GetMapping("/list/admin/{groupId}")
    @ApiOperation(value = "获取群组管理员列表")
    public GlobalResult insertRelation(@PathVariable("groupId") Long groupId){
        List<Long> result = relationService.getGroupAdmin(groupId);
        return GlobalResult.success(result);
    }
}
