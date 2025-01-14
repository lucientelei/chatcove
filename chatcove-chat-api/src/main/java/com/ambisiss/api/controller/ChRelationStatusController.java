//package com.ambisiss.api.controller;
//
//import com.ambisiss.common.dto.ChRelationStatusDto;
//import com.ambisiss.common.global.GlobalPage;
//import com.ambisiss.common.global.GlobalResult;
//import com.ambisiss.system.entity.ChRelationStatus;
//import com.ambisiss.system.service.ChRelationStatusService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author chenxiaoye
// * @since 2023-04-20
// */
//@RestController
//@RequestMapping("/api/chat/chRelationStatus")
//@Api(tags = "好友关系状态接口")
//public class ChRelationStatusController {
//
//    @Autowired
//    private ChRelationStatusService statusService;
//
//    @PostMapping("/insert")
//    @ApiOperation(value = "新增关系状态")
//    public GlobalResult insertRelationStatus(@RequestBody ChRelationStatusDto dto) {
//        int result = statusService.insertStatus(dto);
//        return GlobalResult.success(result);
//    }
//
//    @DeleteMapping("/del")
//    @ApiOperation(value = "删除关系状态")
//    public GlobalResult delRelationStatus(@RequestParam Long id) {
//        int result = statusService.delStatus(id);
//        return GlobalResult.success(result);
//    }
//
//    @PostMapping("/update")
//    @ApiOperation(value = "更新关系状态")
//    public GlobalResult updateRelationStatus(@RequestParam Long id, @RequestBody ChRelationStatusDto dto) {
//        int result = statusService.updateStatus(id, dto);
//        return GlobalResult.success(result);
//    }
//
//    @GetMapping("/list")
//    @ApiOperation(value = "分页多条件查询关系状态")
//    public GlobalResult listPageRelationStatus(@RequestBody ChRelationStatusDto dto,
//                                 @RequestParam(defaultValue = "1") Integer pageNum,
//                                 @RequestParam(defaultValue = "10") Integer pageSize) {
//        List<ChRelationStatus> result = statusService.listPage(dto, pageNum, pageSize);
//        return GlobalResult.success(GlobalPage.restPage(result));
//    }
//}
