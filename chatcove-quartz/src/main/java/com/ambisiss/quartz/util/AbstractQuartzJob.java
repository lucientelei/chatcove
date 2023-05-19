package com.ambisiss.quartz.util;

import com.ambisiss.common.constant.Constants;
import com.ambisiss.common.constant.ScheduleConstants;
import com.ambisiss.common.utils.BeanUtils;
import com.ambisiss.common.utils.ExceptionUtil;
import com.ambisiss.common.utils.SpringUtils;
import com.ambisiss.common.utils.StringUtils;
import com.ambisiss.quartz.pojo.QuartzJob;
import com.ambisiss.quartz.pojo.QuartzJobLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-19 11:59:53
 */
public abstract class AbstractQuartzJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzJob sysJob = new QuartzJob();
        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param quartzJob  系统计划任务
     */
    protected void before(JobExecutionContext context, QuartzJob quartzJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param quartzJob  系统计划任务
     */
    protected void after(JobExecutionContext context, QuartzJob quartzJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final QuartzJobLog quartzJobLog = new QuartzJobLog();
        quartzJobLog.setJobName(quartzJob.getJobName());
        quartzJobLog.setJobGroup(quartzJob.getJobGroup());
        quartzJobLog.setInvokeTarget(quartzJob.getInvokeTarget());
        quartzJobLog.setStartTime(startTime);
        quartzJobLog.setStopTime(new Date());
        long runMs = quartzJobLog.getStopTime().getTime() - quartzJobLog.getStartTime().getTime();
        quartzJobLog.setJobMessage(quartzJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            quartzJobLog.setStatus(Constants.FAIL);
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            quartzJobLog.setExceptionInfo(errorMsg);
        } else {
            quartzJobLog.setStatus(Constants.SUCCESS);
        }

        // 写入数据库当中
//        SpringUtils.getBean(ISysJobLogService.class).addJobLog(quartzJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param quartzJob  系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, QuartzJob quartzJob) throws Exception;

}
