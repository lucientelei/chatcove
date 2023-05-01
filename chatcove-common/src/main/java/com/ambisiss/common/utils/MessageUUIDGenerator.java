package com.ambisiss.common.utils;

import java.util.UUID;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 14:59:07
 */
public class MessageUUIDGenerator {

    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        return uuidStr;
    }

}
