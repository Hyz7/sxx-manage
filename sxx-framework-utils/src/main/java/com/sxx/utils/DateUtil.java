package com.sxx.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 〈一句话功能简述〉<br>
 * 〈处理日期工具类〉
 *
 * @author hyz
 * @create 2019/3/19 0019
 * @since 1.0.0
 */
public class DateUtil {
    public static String getNowFormateDate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }
}
