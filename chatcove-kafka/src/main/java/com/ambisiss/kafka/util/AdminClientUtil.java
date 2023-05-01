package com.ambisiss.kafka.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @Author: chenxiaoye
 * @Description: kafka-AdminClient工具类
 * @Data: 2023-5-1 16:08:05
 */
@Slf4j
public class AdminClientUtil {

    /**
     * 创建AdminClient
     *
     * @return
     */
    public static AdminClient createAdminClient() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        return AdminClient.create(props);
    }

    /**
     * 创建主题
     *
     * @param topicName
     * @param partition
     * @param replication
     */
    public static void createKafkaTopic(String topicName, Integer partition, short replication) {
        if (isTopicExit(topicName)) {
            return;
        }
        AdminClient adminClient = createAdminClient();
        List<NewTopic> topics = new ArrayList<>();

        //创建主题 主题名称、分区数、副本数
        NewTopic newTopic = new NewTopic(topicName, partition, replication);
        topics.add(newTopic);
        CreateTopicsResult result = adminClient.createTopics(topics);
        try {
            result.all().get();
            log.info("创建kafka主题成功，主题名：{}，分区数：{}，副本数：{}", topicName, partition, replication);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除kafka主题
     *
     * @param topicName
     */
    public static void delKafkaTopic(String topicName) {
        AdminClient adminClient = createAdminClient();
        List<String> topics = new ArrayList<>();
        topics.add(topicName);
        DeleteTopicsResult result = adminClient.deleteTopics(topics);
        try {
            result.all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断topic是否存在
     *
     * @param topicName
     * @return
     */
    public static boolean isTopicExit(String topicName) {
        AdminClient adminClient = createAdminClient();
        Set<String> names = null;
        try {
            names = adminClient.listTopics().names().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (names.size() == 0) {
            return false;
        }
        return names.contains(topicName);
    }

    /**
     * 获取kafka主题列表
     */
    public static void listKafkaTopics() {
        AdminClient adminClient = createAdminClient();
        ListTopicsResult result = adminClient.listTopics();
        KafkaFuture<Set<String>> future = result.names();
        try {
            log.info("==================Kafka Topics====================");
            future.get().forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
