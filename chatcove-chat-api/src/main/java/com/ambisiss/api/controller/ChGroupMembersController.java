package com.ambisiss.api.controller;

import com.ambisiss.common.dto.ChGroupMembersDto;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.vo.ChGroupMembersVo;
import com.ambisiss.system.service.ChGroupMembersService;
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
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/api/chat/chGroupMembers")
@Api(tags = "群组成员接口", description = "ChGroupMembersController")
public class ChGroupMembersController {

    @Autowired
    private ChGroupMembersService groupMembersService;

    @PostMapping("/insertMember")
    @ApiOperation(value = "新增群组成员")
    public GlobalResult insertMember(@RequestBody ChGroupMembersDto dto){
        int result = groupMembersService.insertMember(dto);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/delMember")
    @ApiOperation(value = "删除群组成员")
    public GlobalResult delMember(@RequestBody ChGroupMembersDto dto){
        int result = groupMembersService.delMember(dto);
        return GlobalResult.success(result);
    }

    @GetMapping("/listMember")
    @ApiOperation(value = "获取群组成员")
    public GlobalResult listMember(@RequestParam Long groupId){
        List<ChGroupMembersVo> result = groupMembersService.listMember(groupId);
        return GlobalResult.success(result);
    }
}
