package com.ambisiss.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.TimeZone;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 20:49:45
 */
@Configuration
@MapperScan(basePackages = "com.ambisiss.system.mapper")
@ComponentScan(basePackages = {"com.ambisiss.*"})
public class ApplicationConfig {

    /**
     * 时区配置
     *
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperCustomization() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.timeZone(TimeZone.getDefault());
    }

}
