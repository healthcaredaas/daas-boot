package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser.node;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.DataNodeSchema;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.value.factory.ValueHandlerFactory;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.value.handler.IValueHandler;
import cn.healthcaredaas.data.cloud.data.dataformat.utils.NsXmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**

 * @ClassName： XmlNodeDataSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:01
 * @Modify：
 */
@Slf4j
public class XmlNodeDataSerializer extends NodeDataSerializer {

    private Map<String, String> nsMap;

    /**
     * 数据转换为消息
     *
     * @param definition
     * @param bData
     * @return
     */
    @Override
    public String serialize(MessageSchemaDefinition definition, LinkedHashMap<String, Object> bData) throws Exception {
        Document doc = DocumentHelper.parseText(definition.getTemplate());
        nsMap = definition.getNsMap();
        traverseXmlNode(doc, definition.getSchema(), bData);
        return doc.asXML();
    }

    private void traverseXmlNode(Document doc, DataNodeSchema schema, Map<String, Object> bData) throws Exception {
        if (StrUtil.isNotEmpty(schema.getNodePath())) {
            List<DataNodeSchema> children = schema.getChildren();
            if (CollectionUtil.isNotEmpty(children)) {
                if (schema.getIsNested() ==null || BooleanUtil.isFalse(schema.getIsNested())) {
                    for (DataNodeSchema child : children) {
                        traverseXmlNode(doc, child, bData);
                    }
                } else {
                    List<LinkedHashMap<String, Object>> childDatas = (List<LinkedHashMap<String, Object>>) bData.get(schema.getDataset());
                    if (CollectionUtil.isNotEmpty(childDatas)) {
                        Node node2 = NsXmlUtils.getSingleNode(doc, nsMap, schema.getNodePath());
                        Element parentNode = node2.getParent();
                        parentNode.remove(node2);

                        for (LinkedHashMap<String, Object> pdata : childDatas) {
                            Document cloneNode = DocumentHelper.parseText(((Node) node2.clone()).asXML());
                            for (DataNodeSchema child : children) {
                                traverseXmlNode(cloneNode, child, pdata);
                            }
                            parentNode.add(cloneNode.getRootElement());
                        }
                    } else {
                        log.warn("子节点数据为空，XPATH:{}", schema.getNodePath());
                    }
                }
            } else {
                Object value = getNodeValue(schema, bData);
                Node el = NsXmlUtils.getSingleNode(doc, nsMap, schema.getNodePath());
                if (!ObjectUtils.isEmpty(el)) {
                    IValueHandler valueHandler = ValueHandlerFactory.getValueHandler(schema.getDataType());
                    el.setText(String.valueOf(valueHandler.getWritingValue(value, schema.getFormat())));
                }
            }
        }

    }
}
