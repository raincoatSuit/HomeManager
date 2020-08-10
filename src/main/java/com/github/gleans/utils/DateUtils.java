package com.github.gleans.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateUtils {

    /*
    获取当前时间戳
     */
    public static Long getNowTimeStamp() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
