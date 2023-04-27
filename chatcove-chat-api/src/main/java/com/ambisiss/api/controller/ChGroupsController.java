package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChGroupsInsertDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.system.service.ChGroupsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chenxiaoye
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/api/chat/chGroups")
public class ChGroupsController {

    @Autowired
    private ChGroupsService groupsService;

    @PostMapping("/createGroup")
    @ApiOperation(value = "创建群组")
    public GlobalResult createGroup(@RequestBody ChGroupsInsertDto dto) {
        int result = groupsService.insertGroup(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/delGroup/{id}")
    @ApiOperation(value = "解散群组")
    public GlobalResult delGroup(@PathVariable("id") Long id) {
        int result = groupsService.delGroup(id);
        return GlobalResult.success(result);
    }

}
