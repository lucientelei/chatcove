package com.ambisiss.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.ambisiss.common.aspect.AccessLimit;
import com.ambisiss.common.constant.HttpStatus;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.plugin2.util.SystemUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chenxiaoye
 * @Description: 接口放刷拦截器
 * @Data: 2023-6-2 11:41:43
 */
@Slf4j
public class LimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //获取方法中是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            String token = request.getHeader("Authorization");
            if (needLogin) {
                key += "-" + token;
            }
            Integer count = 0;
            //从redis中获取用户访问次数
            if (redisUtil.getCacheObject(key) != null) {
                count = redisUtil.getCacheObject(key);
            }
            if (count == 0) {
                redisUtil.setCacheObject(key, 1);
                redisUtil.expire(key, seconds);
            } else if (count < maxCount) {
                //次数加1
                redisUtil.incr(key);
            } else {
                response.setStatus(HttpStatus.ERROR);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(JSON.toJSONString(GlobalResult.error(HttpStatus.ERROR, "请求次数过多请稍后再试！")));
                return false;
            }
        }
        return true;
    }
}
