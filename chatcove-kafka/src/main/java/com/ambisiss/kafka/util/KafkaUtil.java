package com.ambisiss.kafka.util;

import com.ambisiss.kafka.entity.KafkaConsumerEntity;
import com.ambisiss.kafka.entity.KafkaProducerEntity;
import com.ambisiss.kafka.listener.ProducerCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: chenxiaoye
 * @Description: kafka工具类
 * @Data: 2023-5-1 15:15:48
 */
@Slf4j
public class KafkaUtil {

    /**
     * 初始化 创建生产者队列池
     */
    public static List<KafkaProducerEntity> producerList = new ArrayList<>();

    /**
     * 初始化 创建消费者队列池
     */
    public static List<KafkaConsumerEntity> consumerList = new ArrayList<>();

    /**
     * 初始化队列
     *
     * @param producerCount
     * @param consumerCount
     * @return
     */
    public static Boolean initData(int producerCount, int consumerCount) {
        expansionProducer(producerCount);
        expansionConsumer(consumerCount);
        return true;
    }

    /**
     * 获取空闲的生产者
     *
     * @return
     */
    public static KafkaProducerEntity getProducer() {
        for (KafkaProducerEntity kafkaProducerEntity : producerList) {
            if (!kafkaProducerEntity.isLock()) {
                //设置获取时间并锁定资源
                kafkaProducerEntity.setLastTime();
                kafkaProducerEntity.setLock(true);
                return kafkaProducerEntity;
            }
        }
        //节点资源不足，则扩充
        KafkaProducerEntity kafkaProducerEntity = expansionProducer(1);
        if (kafkaProducerEntity == null) {
            log.error("kafka获取生产者实例失败");
            return null;
        }

        kafkaProducerEntity.setLastTime();
        kafkaProducerEntity.setLock(true);
        return kafkaProducerEntity;
    }

    /**
     * 获取空闲的消费者
     *
     * @return
     */
    public static synchronized KafkaConsumerEntity getConsumer() {
        for (KafkaConsumerEntity kafkaConsumerEntity : consumerList) {
            if (!kafkaConsumerEntity.isLock()) {
                kafkaConsumerEntity.setLastTime();
                kafkaConsumerEntity.setLock(true);
                return kafkaConsumerEntity;
            }
        }

        KafkaConsumerEntity kafkaConsumerEntity = expansionConsumer(100);
        if (kafkaConsumerEntity == null) {
            log.error("kafka获取消费者实例失败");
            return null;
        }

        kafkaConsumerEntity.setLastTime();
        kafkaConsumerEntity.setLock(true);
        return kafkaConsumerEntity;
    }

    /**
     * 根据数量扩充生产者实例
     *
     * @param count
     * @return
     */
    public static KafkaProducerEntity expansionProducer(int count) {
        //配置信息
        Properties props = new Properties();
        try {
            //kafka服务器地址列表
            props.put("bootstrap.servers", "localhost:9092");
            //客户端失败重试次数
            props.put("retries", "3");
            // 生产者打包消息的批量大小，以字节为单位.
            props.put("batch.size", "16384");
            // 生产者缓存内存的大小，以字节为单位.
            props.put("buffer.memory", "33554432");
            //消息key的编解码方式
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            //消息value的编解码方式
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        } catch (Exception e) {
            log.error("系统未配置kafka相关参数：" + e.getCause());
            return null;
        }

        //配置生产者
        KafkaProducer<String, String> producer;
        KafkaProducerEntity kafkaProducerEntity = null;
        for (int i = 0; i < count; i++) {
            producer = new KafkaProducer<String, String>(props);
            kafkaProducerEntity = new KafkaProducerEntity(producer);
            producerList.add(kafkaProducerEntity);
        }

        log.info("-----------------成功拓展" + count + "个kafka生产者实例-----------------");
        return kafkaProducerEntity;
    }

    /**
     * 扩展消费者实例
     *
     * @param count
     * @return
     */
    public static KafkaConsumerEntity expansionConsumer(int count) {
        Properties props = new Properties();
        try {
            // 定义kafka服务器地址列表，不需要指定所有的broker
            props.put("bootstrap.servers", "localhost:9092");
            // 消费者组id
            props.put("group.id", "default-group");
            // 是否自动确认offset
            props.put("enable.auto.commit", "false");
            props.put("auto.offset.reset", "earliest");
            //消息key的编解码方式
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            //消息value的编解码方式
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            //自动确认offset时间间隔
//            props.put("auto.commit.interval.ms", 0);

        } catch (Exception e) {
            log.error("系统未配置 kafka相关参数：" + e.getMessage());
            return null;
        }
        //配置消费者
        KafkaConsumer<String, String> consumer;
        KafkaConsumerEntity kafkaConsumerEntity = null;
        for (int i = 0; i < count; i++) {
            consumer = new KafkaConsumer<String, String>(props);
            kafkaConsumerEntity = new KafkaConsumerEntity(consumer);
            consumerList.add(kafkaConsumerEntity);
        }

        log.info("-------------成功扩展" + count + "个kafka消费者实例。-------------");
        return kafkaConsumerEntity;
    }

    /**
     * 同步阻塞发送
     * 适用场景：业务不需要高吞吐量、更关心消息发送的顺序、不允许消息发送失败
     *
     * @param topic
     * @param message
     * @return
     */
    public static boolean sendBlockMsg(String topic, String message) {
        KafkaProducerEntity kafkaProducerEntity = getProducer();
        if (kafkaProducerEntity == null) {
            log.error("sendBlockMsg--kafka生产者实例获取失败");
            return false;
        }
        //同步发送
        //创建消息
        KafkaProducer<String, String> producer = kafkaProducerEntity.getProducer();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        //发送消息
        Future<RecordMetadata> send = producer.send(record);
        try {
//            RecordMetadata recordMetadata = send.get();
            send.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            kafkaProducerEntity.release();
        }
        return true;
    }


    /**
     * 异步发送(发送并忘记)
     * 适用场景：业务只关心吞吐量、不关心消息发送的顺序、可以允许消息发送失败
     *
     * @param topic
     * @param message
     * @return
     */
    public static boolean sendSyncNioMsg(String topic, String message) {
        KafkaProducerEntity kafkaProducerEntity = getProducer();
        if (kafkaProducerEntity == null) {
            log.error("sendSyncNioMsg--kafka生产者实例获取失败");
            return false;
        }
        KafkaProducer<String, String> producer = kafkaProducerEntity.getProducer();
        //异步发送
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        //发送 回调函数处理
        producer.send(record);
        //刷新
        producer.flush();
        kafkaProducerEntity.release();
        return true;
    }

    /**
     * 异步发送(回调函数)
     * 适用场景：业务需要知道消息发送成功、不关心消息发送的顺序
     *
     * @param topic
     * @param message
     * @return
     */
    public static boolean sendSyncMsg(String topic, String message) {
        KafkaProducerEntity kafkaProducerEntity = getProducer();
        if (kafkaProducerEntity == null) {
            log.error("sendSyncNioMsg--kafka生产者实例获取失败");
            return false;
        }
        KafkaProducer<String, String> producer = kafkaProducerEntity.getProducer();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        //发送，回调函数处理
        producer.send(record, new ProducerCallback(message));
        producer.flush();

        kafkaProducerEntity.release();
        return true;
    }

    /*  ----------------------------消费者---------------------------- */

    /**
     * kafka消费者信息
     *
     * @param kafkaConsumerEntity
     * @param topic
     * @param appointPartition
     * @return
     */
    public static List<String> consumerMsg(KafkaConsumerEntity kafkaConsumerEntity, String topic, Integer appointPartition) {
        //返回消息
        List<String> resultList = new ArrayList<>();
        //创建消费者
        KafkaConsumer<String, String> consumer = kafkaConsumerEntity.getConsumer();
        //TODO 消费消息
        //指定分区
        TopicPartition partition = new TopicPartition(topic, appointPartition);
        //获取已提交的偏移量
        long offset = 0L;
        OffsetAndMetadata offsetAndMetadata = consumer.committed(partition);
        if (offsetAndMetadata != null) {
            offset = offsetAndMetadata.offset();
        }
        //指定偏移量消费
        consumer.assign(Arrays.asList(partition));
        consumer.seek(partition, offset);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
        //打印数据
        for (ConsumerRecord<String, String> record : records) {
            System.out.println("消费的数据为：" + record.value());
            resultList.add(record.value());
        }

        return resultList;
    }

    /**
     * 消费成功
     * 手动提交offset
     *
     * @param kafkaConsumerEntity
     * @return
     */
    public static boolean cousumerCommit(KafkaConsumerEntity kafkaConsumerEntity) {
        KafkaConsumer<String, String> consumer = kafkaConsumerEntity.getConsumer();
        consumer.commitSync();
        kafkaConsumerEntity.release();
        return true;
    }

    /**
     * 创建主题
     *
     * @param topicName   主题名称
     * @param partition   分区数
     * @param replication 副本数量
     */
    public static void createKafkaTopic(String topicName, Integer partition, short replication) {
        AdminClientUtil.createKafkaTopic(topicName, partition, replication);
    }

    /**
     * 删除kafka主题
     * @param topicName
     */
    public static void delKafkaTopic(String topicName) {
        AdminClientUtil.delKafkaTopic(topicName);
    }

    /**
     * 获取主题列表
     */
    public static void listTopic(){
        AdminClientUtil.listKafkaTopics();
    }
}
