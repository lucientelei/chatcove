package com.ambisiss.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: chenxiaoye
 * @Description: 接口放刷注解类
 @AccessLimit(seconds = 5, maxCount = 1)
 * @Data: 2023-6-2 11:38:53
 */
//运行时
@Retention(RetentionPolicy.RUNTIME)
//作用域方法
@Target(ElementType.METHOD)
public @interface AccessLimit {

    /**
     * 失效时间 单位秒
     * @return
     */
    int seconds();

    /**
     * 最大请求次数
     * @return
     */
    int maxCount();

    /**
     * 是否需要登录
     * @return
     */
    boolean needLogin() default true;
}
