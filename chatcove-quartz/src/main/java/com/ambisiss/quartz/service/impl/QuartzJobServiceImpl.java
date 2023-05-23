package com.ambisiss.quartz.service.impl;

import com.ambisiss.common.constant.ScheduleConstants;
import com.ambisiss.common.exception.TaskException;
import com.ambisiss.quartz.mapper.QuartzJobMapper;
import com.ambisiss.quartz.entity.QuartzJob;
import com.ambisiss.quartz.service.QuartzJobService;
import com.ambisiss.quartz.util.ScheduleUtils;
import com.github.pagehelper.PageHelper;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-20 15:28:08
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzJobMapper jobMapper;

    /**
     * 项目启动是，初始化定时器，防止数据为同步到定时任务中导致脏数据
     */
    @PostConstruct
    public void init() {
        try {
            scheduler.clear();
            List<QuartzJob> jobList = jobMapper.selectJobAll();
            for (QuartzJob job : jobList) {
                ScheduleUtils.createScheduleJob(scheduler, job);
            }
        } catch (SchedulerException | TaskException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<QuartzJob> selectJobList(QuartzJob job) {
        PageHelper.startPage(1, 10);
        return jobMapper.selectJobList(job);
    }

    @Override
    public QuartzJob selectJobById(Long jobId) {
        return jobMapper.selectJobById(jobId);
    }

    @Override
    public int pauseJob(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int count = jobMapper.updateJob(job);
        if (count > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return count;
    }

    @Override
    public int deleteJob(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int count = jobMapper.deleteJobById(jobId);
        if (count > 0) {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return count;
    }

    @Override
    public void deleteJobByIds(Long[] ids) throws SchedulerException {
        for (Long id : ids) {
            QuartzJob job = jobMapper.selectJobById(id);
            deleteJob(job);
        }
    }

    @Override
    public int resumeJob(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int count = jobMapper.updateJob(job);
        if (count > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return count;
    }

    @Override
    public int changeStatus(QuartzJob job) throws SchedulerException {
        int count = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            count = resumeJob(job);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            count = pauseJob(job);
        }
        return count;
    }

    @Override
    public void run(QuartzJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        QuartzJob properties = selectJobById(jobId);
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    @Override
    public int insertJob(QuartzJob job) throws SchedulerException, TaskException {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int count = jobMapper.insertJob(job);
        if (count > 0) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return count;
    }

    @Override
    public int updateJob(QuartzJob job) throws SchedulerException, TaskException {
        QuartzJob properties = selectJobById(job.getJobId());
        int count = jobMapper.updateJob(job);
        if (count > 0) {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return count;
    }

    /**
     * 更新任务
     *
     * @param job
     * @param jobGroup
     * @throws SchedulerException
     * @throws TaskException
     */
    public void updateSchedulerJob(QuartzJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getJobId();
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }
}
