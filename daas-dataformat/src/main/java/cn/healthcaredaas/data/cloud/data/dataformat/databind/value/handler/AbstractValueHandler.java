package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler;


public abstract class AbstractValueHandler implements IValueHandler {

    @Override
    public abstract Object getReadingValue(Object srcValue, String destType,
                                           String destFormat);

    @Override
    public abstract Object getWritingValue(Object srcValue, String destFormat);
}
