package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeSchema;
import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeType;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * @ClassName： XmlSchemaUtils.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/7 17:29
 * @Modify：
 */
public class XmlSchemaUtils {

    public static AtomicInteger id = new AtomicInteger(0);

    public static ConcurrentHashMap<String, Integer> tags;
    public static Namespace qNs;

    public static NodeSchema getSchema(String xml) throws DocumentException {
        return getSchema(xml, null, null);
    }

    /**
     * 遍历xml获取xml结构json
     *
     * @param xml
     * @return
     */
    public static NodeSchema getSchema(String xml, String ns, String nsPrefix) throws DocumentException {

        SAXReader saxReader = new SAXReader();
        if (StrUtil.isNotBlank(ns)) {
            qNs = new Namespace(nsPrefix, ns);
        }
        NodeSchema schema = new NodeSchema();
        tags = new ConcurrentHashMap<>();
        id = new AtomicInteger(0);
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
        Element root = document.getRootElement();
        root.setQName(new QName(root.getName(), qNs));
        getChildNodes(root, schema);
        return schema;
    }

    protected static void getChildNodes(Element root, NodeSchema schema) {
        //当前节点的名称、文本内容和属性
        schema.setNodeId(id.getAndIncrement());
        schema.setNodeKey(getTagName(root.getName()));
        schema.setNodeEnName(root.getName());
        schema.setNodePath(root.getPath());
        schema.setNodeType(NodeType.of(root.getNodeTypeName()));
        schema.setNodeName(root.getTextTrim());

        Iterator<Attribute> attrIt = root.attributeIterator();
        List<NodeSchema> array = new ArrayList<>();
        while (attrIt.hasNext()) {
            NodeSchema obj = new NodeSchema();
            Attribute attribute = attrIt.next();
            obj.setNodeId(id.getAndIncrement());
            obj.setNodeKey(getTagName(attribute.getName()));
            obj.setNodeEnName(attribute.getName());
            obj.setNodePath(attribute.getPath());
            obj.setNodeType(NodeType.of(attribute.getNodeTypeName()));
            obj.setNodeName(attribute.getValue());
            obj.setParentNodeId(schema.getNodeId());
            array.add(obj);
        }


        //子节点迭代
        Iterator<Node> it = root.nodeIterator();
        while (it.hasNext()) {
            Node node = it.next();
            if (node instanceof Element) {
                Element elem = (Element) node;
                elem.setQName(new QName(elem.getName(), qNs));
                NodeSchema json = new NodeSchema();
                getChildNodes(elem, json);
                json.setParentNodeId(schema.getNodeId());
                array.add(json);
            } else if (node.getNodeType() != Node.TEXT_NODE) {
                NodeSchema obj = new NodeSchema();
                if (node.getNodeType() == Node.COMMENT_NODE) {
                    obj.setNodeId(id.getAndIncrement());
                    obj.setNodeKey(getTagName(node.getText()));
                    obj.setNodeEnName(node.getText());
                    obj.setNodeType(NodeType.of(node.getNodeTypeName()));
                } else {
                    obj.setNodeId(id.getAndIncrement());
                    obj.setNodeKey(getTagName(node.getName()));
                    obj.setNodeEnName(node.getName());
                    obj.setNodeType(NodeType.of(node.getNodeTypeName()));
                    obj.setNodePath(node.getPath());
                    obj.setNodeType(NodeType.of(node.getNodeTypeName()));
                    obj.setNodeName(node.getText());
                }
                obj.setParentNodeId(schema.getNodeId());
                array.add(obj);
            }
        }

        schema.setChildren(array);

    }

    private static String getTagName(String tag) {
        if (StrUtil.isBlank(tag)) {
            return null;
        }
        String tagName;
        if (tags.get(tag) == null || tags.get(tag) == 0) {
            tagName = tag;
            tags.put(tag, 1);
        } else {
            tagName = tag + "_" + tags.get(tag);
            tags.put(tag, tags.get(tag) + 1);
        }

        return tagName;
    }
}
