package com.ambisiss.kafka.entity;

import cn.hutool.core.date.DateUnit;
import com.ambisiss.common.utils.DateUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 15:06:24
 */
public class KafkaConsumerEntity {

    /**
     * 消费者实例
     */
    private KafkaConsumer<String, String> consumer;

    /**
     * 是否正在被使用
     */
    private boolean isLock;

    /**
     * 最后使用时间
     */
    private String lastTime;

    /**
     * 使用完成释放资源
     *
     * @return
     */
    public boolean release() {
        this.setLastTime();
        this.setLock(false);
        return true;
    }


    public KafkaConsumerEntity(KafkaConsumer<String, String> consumer) {
        super();
        this.consumer = consumer;
        this.isLock = false;
        this.lastTime = DateUtil.getCurrentTime();
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime() {
        this.lastTime = DateUtil.getCurrentTime();
    }
}
