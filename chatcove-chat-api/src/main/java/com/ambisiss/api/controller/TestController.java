package com.ambisiss.api.controller;

import com.ambisiss.common.global.GlobalResult;
import com.ambisiss.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-6-8 11:07:26
 */
@RestController
@RequestMapping("/test")
@Api(tags = "TestController", description = "测试接口")
public class TestController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/getToken")
    @ApiOperation(value = "获取token")
    public GlobalResult getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getClaimFiled(token, "userId");
        System.out.println(userId);
        return GlobalResult.success(token);
    }


}
