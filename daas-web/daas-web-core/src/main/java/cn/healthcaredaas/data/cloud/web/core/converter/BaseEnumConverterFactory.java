package cn.healthcaredaas.data.cloud.web.core.converter;

import cn.healthcaredaas.data.cloud.core.enums.IBaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基础枚举转换器工厂
 *
 * @ClassName： BaseEnumConverterFactory.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/3 16:03
 * @Modify：
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseEnumConverterFactory implements ConverterFactory<String, IBaseEnum> {

    private final ConcurrentMap<Class<? extends IBaseEnum>, BaseEnumConverter> holderMapper = new ConcurrentHashMap<>();

    @Override
    public <T extends IBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        BaseEnumConverter converter = holderMapper.get(targetType);
        if (converter == null) {
            converter = new BaseEnumConverter(targetType);
            holderMapper.put(targetType, converter);
        }
        return (Converter<String, T>) converter;
    }
}