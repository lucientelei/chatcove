package com.ambisiss.api;

import com.ambisiss.kafka.properties.KafkaConsumerProperties;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import com.github.yitter.idgen.YitIdHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ChChatMessageMongoService messageMongoService;

}
