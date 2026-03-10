package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler;

import cn.hutool.core.util.NumberUtil;

/**

 * @ClassName： NumberValueHandler.java
 * @Author： chenpan
 * @Date：2024/4/26 09:50
 * @Modify：
 */
public class NumberValueHandler extends AbstractValueHandler {
    @Override
    public Object getReadingValue(Object srcValue, String destType, String destFormat) {
        return srcValue;
    }

    @Override
    public Object getWritingValue(Object srcValue, String destFormat) {
        if (srcValue == null) {
            return null;
        }
        try {
            return NumberUtil.parseNumber(String.valueOf(srcValue));
        } catch (Exception e) {
            return String.valueOf(srcValue);
        }
    }
}
