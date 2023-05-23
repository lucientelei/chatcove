package com.ambisiss.quartz.controller;

import com.ambisiss.common.constant.Constants;
import com.ambisiss.common.exception.TaskException;
import com.ambisiss.common.global.GlobalPage;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.utils.StringUtils;
import com.ambisiss.quartz.entity.QuartzJob;
import com.ambisiss.quartz.service.QuartzJobService;
import com.ambisiss.quartz.util.CronUtils;
import com.ambisiss.quartz.util.ScheduleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-20 15:58:01
 */
@RestController
@RequestMapping("/api/chat/quartzJob")
@Api(tags = "调度任务信息", description = "QuartzJobController")
public class QuartzJobController {

    @Autowired
    private QuartzJobService jobService;

    @GetMapping("/list")
    @ApiOperation(value = "获取计划任务")
    public GlobalResult list(@RequestBody QuartzJob job) {
        List<QuartzJob> result = jobService.selectJobList(job);
        return GlobalResult.success(GlobalPage.restPage(result));
    }

    @GetMapping("/getById/{jobId}")
    @ApiOperation(value = "通过调度任务ID查询调度信息")
    public GlobalResult getInfo(@PathVariable("jobId") Long jobId) {
        QuartzJob result = jobService.selectJobById(jobId);
        return GlobalResult.success(result);
    }

    /*
    {
      "jobName": "test",
      "jobGroup": "DEFAULT",
      "invokeTarget": "ryTask.ryMultipleParams",
      "cronExpression": "* * * * * ?",
      "misfirePolicy": 1,
      "concurrent": 1,
      "status": "1"
    }
     */
    @PostMapping("/insert")
    @ApiOperation(value = "创建任务")
    public GlobalResult createJob(@RequestBody QuartzJob job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS})) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.HTTP, Constants.HTTPS})) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(job.getInvokeTarget())) {
            return GlobalResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setCreateBy("ADMIN");
        return GlobalResult.success(jobService.insertJob(job));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新任务")
    public GlobalResult updateJob(@RequestBody QuartzJob job) throws SchedulerException, TaskException {
        if (!CronUtils.isValid(job.getCronExpression())) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        } else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI)) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS})) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[]{Constants.HTTP, Constants.HTTPS})) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        } else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR)) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        } else if (!ScheduleUtils.whiteList(job.getInvokeTarget())) {
            return GlobalResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
        }
        job.setUpdateBy("ADMIN");
        return GlobalResult.success(jobService.updateJob(job));
    }

    @PutMapping("/change/status")
    @ApiOperation(value = "修改调度任务状态")
    public GlobalResult changeStatus(@RequestBody QuartzJob job) throws SchedulerException, TaskException {
        QuartzJob newJob = jobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return GlobalResult.success(jobService.updateJob(newJob));
    }

    @DeleteMapping("/del/{ids}")
    @ApiOperation(value = "批量删除任务")
    public GlobalResult deleteJobs(@PathVariable("ids") Long[] ids) throws SchedulerException {
        jobService.deleteJobByIds(ids);
        return GlobalResult.success();
    }

    @PutMapping("/run")
    @ApiOperation(value = "运行定时任务")
    public GlobalResult run(@RequestBody QuartzJob job) throws SchedulerException {
        jobService.run(job);
        return GlobalResult.success();
    }
}
