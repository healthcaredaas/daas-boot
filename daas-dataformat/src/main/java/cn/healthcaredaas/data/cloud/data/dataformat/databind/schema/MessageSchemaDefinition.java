package cn.healthcaredaas.data.cloud.data.dataformat.databind.schema;

import cn.healthcaredaas.data.cloud.data.dataformat.enums.FormatType;
import cn.healthcaredaas.data.cloud.data.dataformat.enums.TransType;
import lombok.Data;

import java.util.Map;

/**

 * @ClassName： MessageDefinition.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 10:06
 * @Modify：
 */
@Data
public class MessageSchemaDefinition {

    private String code;
    private String name;
    private FormatType format;
    private String template;
    private Map<String, String> nsMap;
    private String root;

    private TransType transType;
    private DataNodeSchema schema;
    private GroovyScript script;
}
