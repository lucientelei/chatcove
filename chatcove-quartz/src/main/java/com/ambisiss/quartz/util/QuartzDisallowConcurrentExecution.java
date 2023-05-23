package com.ambisiss.quartz.util;

import com.ambisiss.quartz.entity.QuartzJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @Author: chenxiaoye
 * @Description: 定时任务处理（禁止并发执行）
 * @Data: 2023-5-19 11:59:33
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob{


    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception {
        JobInvokeUtil.invokeMethod(quartzJob);
    }
}
