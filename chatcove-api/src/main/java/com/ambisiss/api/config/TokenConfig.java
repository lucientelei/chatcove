package com.ambisiss.api.config;

import com.ambisiss.common.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 13:24:16
 */
@Configuration
public class TokenConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor())
//                .addPathPatterns("/**");
//    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }
}
