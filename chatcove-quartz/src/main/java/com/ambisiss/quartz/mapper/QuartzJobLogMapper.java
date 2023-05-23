package com.ambisiss.quartz.mapper;

import com.ambisiss.quartz.entity.QuartzJobLog;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-19 12:56:59
 */
public interface QuartzJobLogMapper {

    /**
     * 获取quartz调度器日志的计划任务
     * @param jobLog
     * @return
     */
    List<QuartzJobLog> selectJobLogList(QuartzJobLog jobLog);

    /**
     * 查询所有调度任务日志
     * @return
     */
    List<QuartzJobLog> selectJobLogAll();

    /**
     * 通过调度任务日志ID查询调度信息
     * @param jobLogId
     * @return
     */
    QuartzJobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任务日志
     * @param jobLog
     * @return
     */
    int insertJobLog(QuartzJobLog jobLog);

    /**
     * 删除任务日志
     * @param jobId
     * @return
     */
    int deleteJobLogById(Long jobId);

    /**
     * 批量删除调度日志信息
     * @param logIds
     * @return
     */
    int deleteJobByIds(Long[] logIds);

    /**
     * 清空任务日志
     */
    void cleanJobLog();
}
