package com.ambisiss.api.config;

import com.ambisiss.common.interceptor.LimitInterceptor;
import com.ambisiss.common.interceptor.TokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 13:24:16
 */
@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Bean
    public LimitInterceptor limitInterceptor() {
        return new LimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        log.info("注入interceptors");
        List<String> excludePath = new ArrayList<>();

        // 白名单
        excludePath.add("/book/**");
        excludePath.add("/api/chat/**/login");
        excludePath.add("/api/chat/**/register");
        excludePath.add("/api/chat/mongodb/chChatMessage/**");
        excludePath.add("/api/chat/mongodb/chGroupMessage/**");
//        excludePath.add("/api/chat/chUser/register");
        excludePath.add("/favicon.ico");
        excludePath.add("/error");
        excludePath.add("/static/**");
        excludePath.add("/swagger-ui.html");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/swagger/**");
        excludePath.add("/webjars/**");
        excludePath.add("/v2/**");
        excludePath.add("/csrf/**");

        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        registry.addInterceptor(limitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }

    /**
     * 解决mongodb获取Long类型id失真
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        //将Long转为string 解决id过大 js显示问题
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0, jackson2HttpMessageConverter);
    }
}
