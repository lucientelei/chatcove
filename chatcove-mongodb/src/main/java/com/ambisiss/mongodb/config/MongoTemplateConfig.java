package com.ambisiss.mongodb.config;

import com.ambisiss.mongodb.util.TimestampConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-4 19:56:11
 */
//@Configuration
//@EnableMongoRepositories()
//public class MongoTemplateConfig {
//
//    @Autowired
//    private TimestampConverter timestampConverter;
//
//    private final String URI_PATTERN = "(mongodb.*:)(.*?)(@.+)";
//
//    @Bean
//    public MongoDatabaseFactory mongoDbFactory(MongoProperties properties){
//
//    }
//}
