package cn.healthcaredaas.data.cloud.web.core.converter;

import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import cn.healthcaredaas.data.cloud.core.utils.DateTimeUtils;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description: LocalDateTime时间转换器
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/8/21 08:22
 * @Modify：
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    /**
     * 将字符串解析为 LocalDateTime
     *
     * @param source 字符串
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime convert(String source) {
        return DateTimeUtils.parse(source);
    }
}
