package com.ambisiss.api;

import com.ambisiss.kafka.properties.KafkaConsumerProperties;
import com.ambisiss.kafka.properties.KafkaProducerProperties;
import com.ambisiss.kafka.properties.KafkaProperties;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.github.yitter.idgen.YitIdHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;

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
    public void localTime() {
        System.out.println(LocalDateTime.now());
    }

    @Test
    public void mongoSaveTest() {
        ChChatMessageMongo chatMessage = new ChChatMessageMongo();
        chatMessage.setSenderId(1L);
        chatMessage.setReceiverId(2L);
        chatMessage.setMessageUuid("12312312");
        chatMessage.setMessage("你好");
        chatMessage.setMessageTypeId(1L);
        LocalDateTime now = LocalDateTime.now();
        chatMessage.setCreateTime(now);
        System.out.println(now);
        mongoTemplate.insert(chatMessage);
        System.out.println(chatMessage.getId());
    }

    @Test
    public void mongoListTest() {
        System.out.println("--------------------------------------查询");
        for (ChChatMessageMongo item : mongoTemplate.findAll(ChChatMessageMongo.class)) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void test() {
        System.out.println(YitIdHelper.nextId());
        System.out.println(YitIdHelper.nextId());
        System.out.println(YitIdHelper.nextId());
        System.out.println(YitIdHelper.nextId());
    }
}
