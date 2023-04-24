package com.ambisiss.api.config;

import com.ambisiss.common.interceptor.TokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("注入interceptors");
        List<String> excludePath = new ArrayList<>();

        // 排除拦截，除了注册登录（此时还没TOKEN）,其他都拦截
        excludePath.add("/register");
        excludePath.add("/login/**");
        excludePath.add("/static/**");
        excludePath.add("/assets/**");
        excludePath.add("/assets/**");
        excludePath.add("/swagger-ui.html");
        excludePath.add("/null/**");
        excludePath.add("/swagger-resources/**");
        excludePath.add("/swagger/**");
        excludePath.add("/webjars/**");
        excludePath.add("/v2/**");


        registry.addInterceptor(tokenInterceptor())
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
}
