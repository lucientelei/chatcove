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
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {

    /**
     * 重试次数
     */
    private String retries;

    /**
     * 批次大小 默认16k
     */
    private String batchSize;

    /**
     * 缓冲区大小，默认32M
     */
    private String bufferMemory;

    /**
     * ACK应答级别，指定分区中必须要有多少个副本收到消息之后才会认为消息成功写入，默认为1只要分区的leader副本成功写入消息；0表示不需要等待任何服务端响应；-1或all需要等待ISR中所有副本都成功写入消息
     */
    private String acks;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 指定消息key和消息体的编解码方式
     */
    private String keySerializer;

    /**
     * 指定消息key和消息体的编解码方式
     */
    private String valueSerializer;

    /**
     * 消息压缩方式，默认为none，另外有gzip、snappy、lz4
     */
    private String compressionType;

}
