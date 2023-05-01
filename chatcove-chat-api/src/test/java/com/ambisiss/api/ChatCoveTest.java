package com.ambisiss.api;

import com.ambisiss.kafka.properties.KafkaConsumerProperties;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 16:54:52
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatCoveTest {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaProducerProperties producerProperties;

    @Autowired
    private KafkaConsumerProperties consumerProperties;

    @Test
    public void test(){
        System.out.println(consumerProperties.getKeySerializer());
        System.out.println(consumerProperties.getValueSerializer());
    }

}
