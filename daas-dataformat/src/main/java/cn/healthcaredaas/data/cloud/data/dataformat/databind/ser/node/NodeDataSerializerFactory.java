package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node;


import cn.healthcaredaas.data.cloud.data.dataformat.enums.FormatType;

import java.util.Objects;

/**

 * @ClassName： NodeDataGeneratorFactory.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/6 10:50
 * @Modify：
 */
public class NodeDataSerializerFactory {

    public static NodeDataSerializer getNodeDataSerializer(FormatType type) {
        switch (Objects.requireNonNull(type)) {
            case XML:
                return new XmlNodeDataSerializer();
            case JSON:
                return new JsonNodeDataSerializer();
            default:
                return null;
        }
    }
}
