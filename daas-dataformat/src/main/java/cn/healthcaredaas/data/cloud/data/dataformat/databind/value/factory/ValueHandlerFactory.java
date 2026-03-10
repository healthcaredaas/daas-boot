package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.factory;


import cn.healthcaredaas.data.cloud.core.enums.DataType;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler.*;

public class ValueHandlerFactory {

    public static IValueHandler getValueHandler(DataType type) {
        if (type != null) {
            if (isDateOrTimeType(type)) {
                return new DateValueHandler();
            }
            if (isNumberType(type)) {
                return new NumberValueHandler();
            }
            if (DataType.STRING.equals(type)) {
                return new StringValueHandler();
            }
        }
        return new DefaultValueHandler();
    }

    private static boolean isDateOrTimeType(DataType type) {
        return DataType.DATE.equals(type)
                || DataType.TIME.equals(type)
                || DataType.TIMESTAMP.equals(type)
                || DataType.DATETIME.equals(type);
    }

    private static boolean isNumberType(DataType type) {
        return DataType.NUMBER.equals(type);
    }


}
