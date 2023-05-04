package com.ambisiss.mongodb.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: chenxiaoye
 * @Description: 时间转化功能
 * @Data: 2023-5-4 20:01:18
 */
@Component
public class TimestampConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }
}
