package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node;

import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.DataNodeSchema;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.service.TransformFunctionService;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**

 * @ClassName： NodeDataSerializer.java
 * @Author： chenpan
 * @Date：2024/3/9 14:46
 * @Modify：
 */
public abstract class NodeDataSerializer {

    @Setter
    protected TransformFunctionService transformFunctionService;

    /**
     * 数据转换为消息
     *
     * @param definition
     * @param bData
     * @return
     */
    public abstract String serialize(MessageSchemaDefinition definition, LinkedHashMap<String, Object> bData) throws Exception;

    protected Object getNodeValue(DataNodeSchema schema, Map<String, Object> bData){
        Object value = null;
        if (StrUtil.isBlank(schema.getFixedValue())) {
            if (StrUtil.isNotBlank(schema.getTransformFunction())) {
                if (StrUtil.isBlank(schema.getFunctionParam())) {
                    schema.setFunctionParam(schema.getColumn());
                }
                value = transformFunctionService.invokeFunction(schema.getTransformFunction(), bData, schema.getFunctionParam());
            } else {
                value = bData.getOrDefault(schema.getColumn(), "");
            }
        } else {
            value = schema.getFixedValue();
        }
        return value;
    }

}
