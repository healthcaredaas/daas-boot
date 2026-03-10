package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node.NodeDataSerializer;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node.NodeDataSerializerFactory;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.service.TransformFunctionService;
import lombok.extern.slf4j.Slf4j;

/**

 * @ClassName： DataNodeSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/5 18:00
 * @Modify：
 */
@Slf4j
public class DataNodeSerializer extends AdapterSerializer {

    @Override
    public String serialize() {
        MessageSchemaDefinition definition = this.getTask().getDefinition();
        if (definition == null) {
            log.warn("缺少数据转换定义信息");
            return null;
        }
        if (StrUtil.isEmpty(definition.getTemplate())) {
            log.warn("缺少数据集模板信息，任务：{}", getTask().getName());
            return null;
        }
        if (ObjectUtil.isEmpty(definition.getSchema())) {
            log.warn("缺少节点配置信息，任务：{}", getTask().getName());
            return null;
        }
        NodeDataSerializer serializer = NodeDataSerializerFactory.getNodeDataSerializer(definition.getFormat());
        if (serializer == null) {
            return null;
        }
        TransformFunctionService transformFunctionService = SpringUtil.getBean(TransformFunctionService.class);
        serializer.setTransformFunctionService(transformFunctionService);
        try {
            return serializer.serialize(definition, getData());
        } catch (Exception e) {
            log.error("节点转换异常：{}", e.getMessage());
            return null;
        }
    }
}
