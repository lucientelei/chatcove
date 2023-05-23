package com.ambisiss.quartz.service;

import com.ambisiss.common.exception.TaskException;
import com.ambisiss.quartz.entity.QuartzJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-19 13:03:14
 */
public interface QuartzJobService {

    /**
     * 获取quartz调度器的计划任务
     *
     * @param job
     * @return
     */
    List<QuartzJob> selectJobList(QuartzJob job);

    /**
     * 通过调度任务ID查询调度信息
     *
     * @param jobId
     * @return
     */
    QuartzJob selectJobById(Long jobId);

    /**
     * 暂停任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    int pauseJob(QuartzJob job) throws SchedulerException;

    /**
     * 删除任务-》对应trigger也将被删除
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    int deleteJob(QuartzJob job) throws SchedulerException;

    /**
     * 批量删除调度信息
     *
     * @param ids
     * @return
     * @throws SchedulerException
     */
    void deleteJobByIds(Long[] ids) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    int resumeJob(QuartzJob job) throws SchedulerException;

    /**
     * 修改调度任务状态
     *
     * @param job
     * @return
     * @throws SchedulerException
     */
    int changeStatus(QuartzJob job) throws SchedulerException;

    /**
     * 立即运行任务
     * @param job
     * @throws SchedulerException
     */
    void run(QuartzJob job) throws SchedulerException;

    /**
     * 新增任务
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    int insertJob(QuartzJob job) throws SchedulerException, TaskException;

    /**
     * 更新任务的时间表达式
     * @param job
     * @return
     * @throws SchedulerException
     * @throws TaskException
     */
    int updateJob(QuartzJob job) throws SchedulerException, TaskException;

}
