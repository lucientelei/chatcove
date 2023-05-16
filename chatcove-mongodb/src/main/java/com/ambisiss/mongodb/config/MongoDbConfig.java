//package com.ambisiss.mongodb.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import javax.annotation.PostConstruct;
//import java.util.Date;
//import java.util.TimeZone;
//
///**
// * @Author: chenxiaoye
// * @Description:
// * @Data: 2023-5-7 23:38:30
// */
//@Configuration
//public class MongoDbConfig {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @PostConstruct
//    public void setMongoTemplateTimezone() {
//        TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
//        mongoTemplate.getConverter().getMappingContext();
//
//    }
//}
