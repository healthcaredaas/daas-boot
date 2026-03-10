package cn.healthcaredaas.data.cloud.data.dataformat.databind.schema;

import lombok.Data;

/**

 * @ClassName： DataTask.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:14
 * @Modify：
 */
@Data
public class DataTask {
    private String id;
    private String name;
    private MessageSchemaDefinition definition;
}
