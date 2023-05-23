package com.ambisiss.quartz.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.quartz.entity.QuartzJobLog;
import com.ambisiss.quartz.service.QuartzJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 09:29:33
 */
@RequestMapping("/api/chat/quartzJobLog")
@Api(tags = "调度任务信息日志", description = "QuartzJobLogController")
public class QuartzJobLogController {

    @Autowired
    private QuartzJobLogService jobLogService;

    @GetMapping("/list")
    @ApiOperation(value = "查询定时任务调度日志列表")
    public GlobalResult list(@RequestBody QuartzJobLog jobLog) {
        List<QuartzJobLog> result = jobLogService.selectJobLogList(jobLog);
        return GlobalResult.success(result);
    }

    @GetMapping("/{jobLogId}")
    @ApiOperation(value = "通过ID查询日志信息")
    public GlobalResult getInfo(@PathVariable("jobLogId") Long jobLogId) {
        QuartzJobLog result = jobLogService.selectJobLogById(jobLogId);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/{jobLogIds}")
    @ApiOperation(value = "批量删除调度日志信息")
    public GlobalResult delete(@PathVariable("jobLogIds") Long[] jobLogIds) {
        int result = jobLogService.deleteJobLogByIds(jobLogIds);
        return GlobalResult.success(result);
    }

    @DeleteMapping("/clear")
    @ApiOperation(value = "清空日志")
    public GlobalResult clear(){
        jobLogService.cleanJobLog();
        return GlobalResult.success();
    }

}
