package cn.healthcaredaas.data.cloud.data.dataformat.databind.schema;

import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeSchema;
import lombok.Data;

import java.util.List;

/**

 * @ClassName： DataNodeSchema.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 09:58
 * @Modify：
 */
@Data
public class DataNodeSchema extends NodeSchema {

    private String dataset;

    private String column;

    private String transformFunction;

    private String functionParam;

    @Override
    public List<DataNodeSchema> getChildren() {
        return getChildren();
    }
}
