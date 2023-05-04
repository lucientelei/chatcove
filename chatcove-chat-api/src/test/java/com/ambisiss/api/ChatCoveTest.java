package com.ambisiss.api;

import com.ambisiss.kafka.properties.KafkaConsumerProperties;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import com.ambisiss.mongodb.entity.ChChatMessage;
import com.ambisiss.mongodb.repository.ChChatMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalTime;
import java.util.Date;

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

    @Test
    public void mongoSaveTest() {
        ChChatMessage chatMessage = new ChChatMessage();
        chatMessage.setSenderId(1L);
        chatMessage.setReceiverId(2L);
        chatMessage.setMessageUuid("12312312");
        chatMessage.setMessage("你好");
        chatMessage.setMessageTypeId(1L);
        mongoTemplate.insert(chatMessage);
    }

    @Test
    public void mongoListTest() {
        for (ChChatMessage item : mongoTemplate.findAll(ChChatMessage.class)) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void test() {
        System.out.println(consumerProperties.getKeyDeserializer());
        System.out.println(consumerProperties.getValueDeserializer());
    }

}
