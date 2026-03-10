package cn.healthcaredaas.data.cloud.web.core.converter;

import cn.healthcaredaas.data.cloud.core.enums.EnableStatusEnum;
import org.springframework.core.convert.converter.Converter;

/**

 * @ClassName： EnableStatusEnumConverter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 15:13
 * @Modify：
 */
public class EnableStatusEnumConverter implements Converter<String, EnableStatusEnum> {

    @Override
    public EnableStatusEnum convert(String source) {
        return EnableStatusEnum.of(source);
    }
}
