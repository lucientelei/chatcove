package com.ambisiss.kafka.config;

import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import com.ambisiss.kafka.util.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenxiaoye
 * @Description: kafka生产者配置类
 * @Data: 2023-5-1 17:16:49
 */
@Slf4j
@Configuration
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaProducerProperties producerProperties;


    public Map<String, Object> producerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        properties.put("acks", producerProperties.getAcks());
        properties.put("retries", producerProperties.getRetries());
        properties.put("batch.size", producerProperties.getBatchSize());
        properties.put("buffer.memory", producerProperties.getBufferMemory());
        properties.put("key.serializer", producerProperties.getKeySerializer());
        properties.put("value.serializer", producerProperties.getValueSerializer());
        return properties;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        log.info("---------配置KafkaTemplate---------");
        KafkaUtil.createKafkaTopic(KafkaConstant.GROUP_TOPIC, KafkaConstant.PARTITION, KafkaConstant.REPLICATION);
        KafkaUtil.createKafkaTopic(KafkaConstant.PERSONAL_TOPIC, KafkaConstant.PARTITION, KafkaConstant.REPLICATION);
        return new KafkaTemplate<>(producerFactory());
    }
}
