package com.ambisiss.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import javax.swing.text.Document;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 15:59:14
 */
@Slf4j
public class ProducerCallback implements Callback {

    /**
     * 发送的消息
     */
    private String msg;

    public ProducerCallback(String msg) {
        super();
        this.msg = msg;
    }


    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            log.error("kafka生产消息发送异常：" + e.getMessage());
        } else {
            log.info("发送成功：" + msg);
        }
    }
}
