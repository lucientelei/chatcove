package com.ambisiss.kafka.config;

import com.ambisiss.kafka.listener.ConsumerListener;
import com.ambisiss.kafka.properties.KafkaConsumerProperties;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenxiaoye
 * @Description: kafka消费者配置
 * @Data: 2023-5-1 17:26:38
 */
@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaConsumerProperties consumerProperties;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServers());
        //获取服务器Ip作为groupId
        properties.put("group.id", consumerProperties.getGroupId());
        properties.put("enable.auto.commit", consumerProperties.getEnableAutoCommit());
        properties.put("auto.commit.interval.ms", consumerProperties.getAutoCommitInterval());
        properties.put("auto.offset.reset", consumerProperties.getAutoOffsetReset());
        properties.put("key.deserializer", consumerProperties.getKeyDeserializer());
        properties.put("value.deserializer", consumerProperties.getValueDeserializer());
        return properties;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
//        log.info("---------配置KafkaListener---------");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(1500);
        //开启批量监听
        factory.setBatchListener(true);
        //配置手动提交offset 手动调用Acknowledgment.acknowledge()后立即提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}