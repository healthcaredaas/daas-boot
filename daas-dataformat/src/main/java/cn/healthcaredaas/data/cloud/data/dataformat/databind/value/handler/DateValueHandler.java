package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler;

import cn.hutool.core.date.DateUtil;

import java.util.Date;


public class DateValueHandler extends AbstractValueHandler {

    @Override
    public Object getReadingValue(Object srcValue, String destType,
                                  String destFormat) {
        try {
            return DateUtil.parse((String) srcValue, destFormat);
        } catch (Exception e) {
            return srcValue;
        }

    }

    @Override
    public Object getWritingValue(Object srcValue, String destFormat) {
        if (srcValue instanceof Date) {
            return DateUtil.format((Date) srcValue, destFormat);
        }
        return srcValue == null ? "" : srcValue.toString();
    }

}
