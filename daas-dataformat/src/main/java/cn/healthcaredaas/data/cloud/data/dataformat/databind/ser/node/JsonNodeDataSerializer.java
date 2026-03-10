package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.DataNodeSchema;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.value.factory.ValueHandlerFactory;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler.IValueHandler;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**

 * @ClassName： JsonNodeDataSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:02
 * @Modify：
 */
@Slf4j
public class JsonNodeDataSerializer extends NodeDataSerializer {
    /**
     * 数据转换为消息
     *
     * @param definition
     * @param bData
     * @return
     */
    @Override
    public String serialize(MessageSchemaDefinition definition, LinkedHashMap<String, Object> bData) throws Exception {
        JSONObject object = JSONObject.parseObject(definition.getTemplate());
        traverseJsonNode(object, definition.getSchema(), bData);
        return object.toJSONString();
    }

    /**
     * 递归JSONPATH赋值
     *
     * @param object
     * @param schema
     * @param bData
     * @throws Exception
     */
    private void traverseJsonNode(JSONObject object, DataNodeSchema schema, Map<String, Object> bData) throws Exception {
        if (StrUtil.isNotEmpty(schema.getNodePath())) {
            List<DataNodeSchema> children = schema.getChildren();
            if (CollectionUtil.isNotEmpty(children)) {
                if (schema.getIsNested() == null || BooleanUtil.isFalse(schema.getIsNested())) {
                    for (DataNodeSchema child : children) {
                        traverseJsonNode(object, child, bData);
                    }
                } else {
                    List<LinkedHashMap<String, Object>> childDatas = (List<LinkedHashMap<String, Object>>) bData.get(schema.getDataset());
                    if (CollectionUtil.isNotEmpty(childDatas)) {
                        JSONArray array = (JSONArray) JSONPath.eval(object, schema.getNodePath());
                        String tmpChild = array.get(0).toString();
                        array.remove(0);

                        for (LinkedHashMap<String, Object> pdata : childDatas) {
                            JSONObject cloneNode = JSONObject.parseObject(tmpChild);

                            for (DataNodeSchema child : children) {
                                traverseJsonNode(cloneNode, child, pdata);
                            }
                            array.add(cloneNode);
                        }
                    } else {
                        log.warn("子节点数据为空，JPATH:{}", schema.getNodePath());
                    }
                }
            } else {
                Object value = getNodeValue(schema, bData);
                if (JSONPath.contains(object, schema.getNodePath())) {
                    IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(schema.getDataType());
                    value = valueHandler.getWritingValue(value, schema.getFormat());
                    JSONPath.set(object, schema.getNodePath(), value);
                }
            }
        }
    }
}
