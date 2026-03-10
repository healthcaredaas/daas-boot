package cn.healthcaredaas.data.cloud.web.core.converter;

import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import org.springframework.core.convert.converter.Converter;

/**

 * @ClassName： EnableStatusEnumConverter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 15:13
 * @Modify：
 */
public class SuccessStatusEnumConverter implements Converter<String, SuccessStatusEnum> {

    @Override
    public SuccessStatusEnum convert(String source) {
        return SuccessStatusEnum.of(source);
    }
}
