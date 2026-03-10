package cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler;

/**

 * @ClassName： IValueHandler.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:25
 * @Modify：
 */
public interface IValueHandler {

    Object getReadingValue(Object srcValue, String destType, String destFormat);

    Object getWritingValue(Object srcValue, String destFormat);
}
