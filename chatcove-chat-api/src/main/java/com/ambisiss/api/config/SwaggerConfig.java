package com.ambisiss.api.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 18:49:15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${server.port}")
    private String port;

    /**
     * 定义分隔符
     */
    private static final String splitor = ";";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


    @Bean
    public Docket createRestApi(Environment environment) {
        //设置要显⽰的Swagger环境
        Profiles profiles = Profiles.of("dev", "test");
        //通过environment.acceptsProfiles 判断是否处在⾃⼰设定的环境当中
        boolean flag = environment.acceptsProfiles(profiles);
        if (flag) {
            log.info("......Swagger2启动于 localhost:" + port + "/swagger-ui.html");
        }
//        String basePackages = "com.ambisiss.api.controller" + splitor + "com.ambisiss.quartz.controller";
        String basePackages = "com.ambisiss.quartz.controller" + splitor + "com.ambisiss.api.controller" + splitor + "com.ambisiss.mongodb.controller";
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .enable(flag)
                .select()
                .apis(basePackage(basePackages))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .apiInfo(apiInfo());
    }

    /**
     * 声明基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * 校验基础包
     *
     * @param basePackage 基础包路径
     * @return
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * 检验基础包实例
     *
     * @param requestHandler 请求处理类
     * @return
     */
    @SuppressWarnings("deprecation")
    private static Optional<? extends Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.fromNullable(requestHandler.declaringClass());
    }

    /**
     * 构建 api文档的详细信息函数
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("ambisiss", "", "q799774821@163.com");
        return new ApiInfoBuilder()
                .title("ChatRove API")
                .contact(contact)
                .description("ChatRove API接口")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }
}
