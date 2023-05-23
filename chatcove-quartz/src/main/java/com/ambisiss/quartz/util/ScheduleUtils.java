package com.ambisiss.quartz.util;

import com.ambisiss.common.constant.Constants;
import com.ambisiss.common.constant.ScheduleConstants;
import com.ambisiss.common.exception.TaskException;
import com.ambisiss.common.utils.SpringUtils;
import com.ambisiss.common.utils.StringUtils;
import com.ambisiss.quartz.entity.QuartzJob;
import org.quartz.*;

/**
 * @Author: chenxiaoye
 * @Description: 定时任务工具类
 * @Data: 2023-5-19 12:10:02
 */
public class ScheduleUtils {

    /**
     * 得到quartz任务类
     *
     * @param quartzJob 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(QuartzJob quartzJob) {
        boolean isConcurrent = "0".equals(quartzJob.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     *
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     *
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     *
     * @param scheduler
     * @param job
     */
    public static void createScheduleJob(Scheduler scheduler, QuartzJob job) throws TaskException, SchedulerException {
        Class<? extends Job> jobClass = getQuartzJobClass(job);
        //构建job信息
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        //表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

        //安心的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();
        //放入参数，运行时的方法可获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            //防止创建时存在数据问题，先移除，后再执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        //暂停任务
        if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 设置定时任务策略
     *
     * @param job
     * @param cb
     * @return
     * @throws TaskException
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(QuartzJob job, CronScheduleBuilder cb) throws TaskException {
        switch (job.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + job.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }

    /**
     * 检查包名是否为白名单配置
     *
     * @param invokeTarget
     * @return
     */
    public static boolean whiteList(String invokeTarget) {
        String packageName = StringUtils.substringBefore(invokeTarget, "(");
        int count = StringUtils.countMatches(packageName, ".");
        if (count > 1) {
            StringUtils.containsAnyIgnoreCase(invokeTarget, Constants.JOB_WHITELIST_STR);
        }
        Object obj = SpringUtils.getBean(StringUtils.split(invokeTarget, ".")[0]);
        return StringUtils.containsAnyIgnoreCase(obj.getClass().getPackage().getName(), Constants.JOB_WHITELIST_STR);
    }

}
