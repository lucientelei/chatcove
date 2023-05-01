package com.ambisiss.kafka.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 16:34:28
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    /**
     * 节点
     */
    private String bootstrapServers;
}
