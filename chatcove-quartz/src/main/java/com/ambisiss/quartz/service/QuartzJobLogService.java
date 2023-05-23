package com.ambisiss.quartz.service;

import com.ambisiss.quartz.entity.QuartzJobLog;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 09:29:14
 */
public interface QuartzJobLogService {

    /**
     * 获取quartz调度日志的计划任务
     *
     * @param jobLog
     * @return
     */
    List<QuartzJobLog> selectJobLogList(QuartzJobLog jobLog);

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId
     * @return
     */
    QuartzJobLog selectJobLogById(Long jobLogId);

    /**
     * 添加任务日志
     *
     * @param jobLog
     */
    void addJobLog(QuartzJobLog jobLog);

    /**
     * 批量删除调度日志信息
     *
     * @param jobIds
     * @return
     */
    int deleteJobLogByIds(Long[] jobIds);

    /**
     * 删除任务日志
     *
     * @param jobId
     * @return
     */
    int deleteJobLogById(Long jobId);

    /**
     * 清空任务日志
     */
    void cleanJobLog();
}
