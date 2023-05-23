package com.ambisiss.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-20 17:34:15
 */
@Slf4j
@Component("quartzTask")
public class QuartzTask {

    public void test() {
        log.info("执行QuartzTask.test方法");
    }

}
