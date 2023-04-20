package com.ambisiss.common.global;

import com.ambisiss.common.constant.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-20 19:15:41
 */
public class GlobalResult extends HashMap<String, Object> {

    private static final Long serialVersionUID = 1L;

    /* 状态码 */
    public static final String CODE_TAG = "code";
    /* 返回内容 */
    public static final String MSG_TAG = "msg";
    /* 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 GlobalResult 对象，使其表示一个空消息。
     */
    public GlobalResult() {

    }

    /**
     * 初始化一个新创建的 GlobalResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public GlobalResult(String code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 GlobalResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public GlobalResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isEmpty(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static GlobalResult success() {
        return GlobalResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static GlobalResult success(Object data) {
        return GlobalResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static GlobalResult success(String msg) {
        return GlobalResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static GlobalResult success(String msg, Object data) {
        return new GlobalResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static GlobalResult error() {
        return GlobalResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static GlobalResult error(String msg) {
        return GlobalResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static GlobalResult error(String msg, Object data) {
        return new GlobalResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static GlobalResult error(int code, String msg) {
        return new GlobalResult(code, msg, null);
    }
}

