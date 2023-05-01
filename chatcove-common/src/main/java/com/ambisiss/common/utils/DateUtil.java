package com.ambisiss.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-5-1 15:12:36
 */
public class DateUtil {

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_PATTERN);
    }

    public static String getCurrentTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println("Current time: " + getCurrentTime());
        System.out.println("Current time in pattern yyyy/MM/dd HH:mm:ss: " + getCurrentTime("yyyy/MM/dd HH:mm:ss"));
        Date now = new Date();
        System.out.println("Add 3 days to current time: " + addDays(now, 3));
        System.out.println("Add 30 minutes to current time: " + addMinutes(now, 30));
    }

}
