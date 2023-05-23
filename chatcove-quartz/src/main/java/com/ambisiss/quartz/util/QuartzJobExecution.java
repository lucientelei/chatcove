package com.ambisiss.quartz.util;

import com.ambisiss.quartz.entity.QuartzJob;
import org.quartz.JobExecutionContext;

/**
 * @Author: chenxiaoye
 * @Description: 定时任务处理（允许并发执行）
 * @Data: 2023-5-19 12:09:09
 */
public class QuartzJobExecution extends AbstractQuartzJob{
    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception {
        JobInvokeUtil.invokeMethod(quartzJob);
    }
}
