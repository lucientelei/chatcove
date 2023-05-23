package com.ambisiss.quartz.service.impl;

import com.ambisiss.quartz.entity.QuartzJobLog;
import com.ambisiss.quartz.mapper.QuartzJobLogMapper;
import com.ambisiss.quartz.service.QuartzJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 09:29:21
 */
@Service
public class QuartzJobLogServiceImpl implements QuartzJobLogService {

    @Autowired
    private QuartzJobLogMapper jobLogMapper;

    @Override
    public List<QuartzJobLog> selectJobLogList(QuartzJobLog jobLog) {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    @Override
    public QuartzJobLog selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    @Override
    public void addJobLog(QuartzJobLog jobLog) {
        jobLogMapper.insertJobLog(jobLog);
    }

    @Override
    public int deleteJobLogByIds(Long[] jobIds) {
        return jobLogMapper.deleteJobByIds(jobIds);
    }

    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }
}
