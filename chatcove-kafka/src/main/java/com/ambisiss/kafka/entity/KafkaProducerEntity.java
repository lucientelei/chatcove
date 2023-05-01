package com.ambisiss.kafka.entity;

import com.ambisiss.common.utils.DateUtil;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 15:14:24
 */
public class KafkaProducerEntity {

    /**
     * 消费者实例
     */
    private KafkaProducer<String, String> producer;

    /**
     * 是否正在被使用
     */
    private boolean isLock;

    /**
     * 最后使用时间
     */
    private String lastTime;

    public KafkaProducerEntity(KafkaProducer<String, String> producer) {
        super();
        this.producer = producer;
        this.isLock = false;
        this.lastTime = DateUtil.getCurrentTime();
    }

    /**
     * 使用完成，进行资源释放，回归资源池
     *
     * @return
     */
    public boolean release() {
        //释放资源
        this.setLastTime();
        this.setLock(false);
        return true;
    }

    public KafkaProducer<String, String> getProducer() {
        return producer;
    }

    public void setProducer(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime() {
        this.lastTime = DateUtil.getCurrentTime();
    }


}
