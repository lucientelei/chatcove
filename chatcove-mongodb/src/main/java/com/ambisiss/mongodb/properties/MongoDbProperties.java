package com.ambisiss.mongodb.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-6 09:38:09
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoDbProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String database;


}
