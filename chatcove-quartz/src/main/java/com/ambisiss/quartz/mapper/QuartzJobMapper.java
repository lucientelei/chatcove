package com.ambisiss.quartz.mapper;

import com.ambisiss.quartz.entity.QuartzJob;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-19 12:34:02
 */
public interface QuartzJobMapper {

    /**
     * 查询调度任务日志集合
     * @param job   调度信息
     * @return      操作日志集合
     */
    List<QuartzJob> selectJobList(QuartzJob job);


    /**
     * 查看所有调度任务
     * @return
     */
    List<QuartzJob> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     * @param jobId
     * @return
     */
    QuartzJob selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     * @param jobId
     * @return
     */
    int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     * @param ids
     * @return
     */
    int deleteJobByIds(List<Long> ids);

    /**
     * 修改调度任务信息
     * @param job
     * @return
     */
    int updateJob(QuartzJob job);

    /**
     * 新增调度任务信息
     * @param job
     * @return
     */
    int insertJob(QuartzJob job);
}
