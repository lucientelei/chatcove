package com.ambisiss.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ambisiss.common.constant.HttpStatus;
import com.ambisiss.common.constant.RedisConstant;
import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.common.utils.RedisUtil;
import com.ambisiss.common.vo.ChUserVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-23 11:59:31
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 前置拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //servlet请求响应转换
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JSON.toJSONString(GlobalResult.error(HttpStatus.UNAUTHORIZED, "未登录")));
            return false;
        }
        //跨域时会首先发送一个option请求，给option请求直接返回正常状态 200
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.SUCCESS);
            return false;
        }
        /*
        用户登录成功 生成token给到用户, 同时存储到redis中（key值为用户名（标识）） value为生成的token
        用户再次访问系统请求参数中带有token信息 后台通过过滤器进行拦截进行比对
        如果token匹配成功 就放行 匹配不成功 说明两个token不一致
        开始比对对应的时间戳 后者时间戳 大于前者就把当前token覆盖
        （如果旧的token请求再次进来 期时间戳就晚于当前redis中的token时间（token已经更新）判断其为被踢出的用户提示重新登录）
         */
        String userId = JwtUtils.getClaimFiled(token, "userId");
        String userToken = "";
        ChUserVo chUserVo = null;
        if (!StringUtils.isEmpty(userId)) {
            chUserVo = JSON.parseObject(redisUtil.getCacheObject(RedisConstant.USER_PREFIX + userId), ChUserVo.class);
            userToken = chUserVo.getToken();
        }
        //当前token与redis存储的用户token不一致
        if (!token.equals(userToken)) {
            Date tokenIssueAt = JwtUtils.getIssueAt(token);
            Date userTokenIssueAt = JwtUtils.getIssueAt(userToken);
            if (tokenIssueAt.after(userTokenIssueAt)) {
                redisUtil.setCacheObject(RedisConstant.USER_PREFIX + chUserVo.getId(), JSON.toJSONString(chUserVo));
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //添加跨域支持
        this.fillCorsHeader(request, response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 跨域请求配置
     *
     * @param request
     * @param response
     */
    protected void fillCorsHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    }
}
