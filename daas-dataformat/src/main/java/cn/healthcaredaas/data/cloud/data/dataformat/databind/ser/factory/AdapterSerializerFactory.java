package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.factory;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.*;
import cn.healthcaredaas.data.cloud.data.dataformat.enums.TransType;

/**

 * @ClassName： AdapterSerializerFactory.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 15:14
 * @Modify：
 */
public class AdapterSerializerFactory {

    public static AdapterSerializer getSerializer(TransType type) {
        switch (type) {
            default:
                return new MapToJsonSerializer();
            case Node:
                return new DataNodeSerializer();
            case Groovy:
                return new GroovySerializer();
            case ToJSON:
                return new MapToJsonSerializer();
            case ToXML:
                return new MapToXmlSerializer();
        }
    }
}
