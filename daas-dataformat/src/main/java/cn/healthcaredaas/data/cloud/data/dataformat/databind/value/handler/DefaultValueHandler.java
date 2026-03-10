package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler;


public class DefaultValueHandler extends AbstractValueHandler {

    @Override
    public Object getReadingValue(Object srcValue, String destType,
                                  String destFormat) {
        return srcValue;
    }

    @Override
    public Object getWritingValue(Object srcValue, String destFormat) {
        return srcValue == null ? "" : srcValue.toString();
    }
}
