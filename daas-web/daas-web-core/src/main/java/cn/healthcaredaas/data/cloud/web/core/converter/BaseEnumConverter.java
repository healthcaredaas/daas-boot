package cn.healthcaredaas.data.cloud.web.core.converter;

import cn.hutool.core.util.ObjectUtil;
import cn.healthcaredaas.data.cloud.core.enums.IBaseEnum;
import org.springframework.core.convert.converter.Converter;

/**
 * 基础枚举转换器
 *
 * @ClassName： BaseEnumConverter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/3 15:32
 * @Modify：
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseEnumConverter implements Converter<String, IBaseEnum> {

    private final Class<? extends IBaseEnum> enumType;

    public BaseEnumConverter(Class<? extends IBaseEnum> enumType) {
        this.enumType = enumType;
    }

    @Override
    public IBaseEnum convert(String source) {
        if (ObjectUtil.isEmpty(source)) {
            return null;
        }
        return IBaseEnum.of(enumType, source);
    }
}