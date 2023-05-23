package com.ambisiss.common.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-21 10:15:36
 */
@ApiModel(description = "cron表达体")
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TaskScheduleModel extends Model<TaskScheduleModel> {

    /**
     * 所选作业类型:
     * 0  -> 每秒
     * 1  -> 每天
     * 2  -> 每月
     * 3  -> 每周
     * 4  -> 每分钟
     */
    Integer jobType;

    /**
     * 一周的哪几天
     */
    Integer[] dayOfWeeks;

    /**
     * 一个月的哪几天
     */
    Integer[] dayOfMonths;

    /**
     * 秒
     */
    Integer second;

    /**
     * 分
     */
    Integer minute;

    /**
     * 时
     */
    Integer hour;

}
