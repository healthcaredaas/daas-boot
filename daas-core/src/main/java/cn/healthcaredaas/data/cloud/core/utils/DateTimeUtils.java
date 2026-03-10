package cn.healthcaredaas.data.cloud.core.utils;

import cn.hutool.core.util.ObjectUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: 日期时间工具类
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/27 18:46
 * @Modify：
 */
public class DateTimeUtils {

    /**
     * 计算两个 LocalDateTime 之间相差的毫秒数
     *
     * @param startInclusive 起始时间（包含）
     * @param endExclusive   结束时间（不包含）
     * @return 相差的毫秒数
     */
    public static long between(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        return Duration.between(startInclusive, endExclusive).toMillis();
    }

    /**
     * 将字符串解析为 LocalDateTime
     *
     * @param dateTime 字符串
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String dateTime) {
        if (ObjectUtil.isEmpty(dateTime)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatterConstant.DATETIME_FORMAT);
        return LocalDateTime.parse(dateTime, formatter);
    }
}
