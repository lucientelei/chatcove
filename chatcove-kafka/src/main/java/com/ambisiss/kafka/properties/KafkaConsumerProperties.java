package com.ambisiss.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 16:53:43
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {

    /**
     * 消费者组名称
     */
    private String groupId;

    /**
     * 自动提交消费位移时间隔时间
     */
    private String autoCommitInterval;

    /**
     * 是否自动确认
     */
    private String enableAutoCommit;

    /**
     * 批量消费每次最多消费多少条消息
     */
    private String maxPollRecords;


    /**
     * 指定消息key和消息体的编解码方式
     */
    private String keyDeserializer;

    /**
     * 指定消息key和消息体的编解码方式
     */
    private String valueDeserializer;

    /**
     * 最大等待时间
     */
    private String fetchMaxWait;

    /**
     * 最小消费字节数
     */
    private String fetchMinWait;

    private String autoOffsetReset;

}
