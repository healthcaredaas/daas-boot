package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeSchema;
import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeType;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**

 * @ClassName： JsonSchemaUtils.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/7 17:29
 * @Modify：
 */
public class JsonSchemaUtils {

    public static AtomicInteger id = new AtomicInteger(0);

    public static ConcurrentHashMap<String, Integer> tags;

    public static final String JSON_ROOT = "$";

    public static NodeSchema getSchema(String jsonStr) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        NodeSchema nodeSchema = new NodeSchema();
        tags = new ConcurrentHashMap<>();
        id = new AtomicInteger(0);
        recursive(json, JSON_ROOT, nodeSchema);
        return nodeSchema;
    }

    public static void recursive(Object json, String path, NodeSchema schema) {
        List<NodeSchema> array = new ArrayList<>();
        if (json instanceof JSONArray) {
            JSONArray objArray = (JSONArray) json;
            boolean arrIndex = objArray.size() > 1;
            for (int i = 0; i < objArray.size(); i++) {
                recursive(objArray.get(i), arrIndex ? path + "[" + i + "]" : path, schema);
                schema.setNodeType(NodeType.Array);

            }
        } else if (json instanceof JSONObject) {
            schema.setNodeId(id.getAndIncrement());
            schema.setNodeType(NodeType.Object);
            schema.setNodePath(path);
            schema.setChildren(array);

            for (Map.Entry<String, Object> entry : ((JSONObject) json).entrySet()) {
                Object o = entry.getValue();
                String thisPath = path + "." + entry.getKey();
                NodeSchema obj = new NodeSchema();

                obj.setNodeKey(getTagName(entry.getKey()));
                obj.setNodeEnName(entry.getKey());
                obj.setNodePath(thisPath);
                obj.setNodeType(NodeType.Object);
                obj.setNodeId(id.getAndIncrement());

                if (o instanceof JSONObject) {
                    recursive(o, thisPath, obj);
                    obj.setParentNodeId(schema.getNodeId());
                    obj.setNodeType(NodeType.Object);
                    array.add(obj);
                } else if (o instanceof JSONArray) {
                    recursive(o, thisPath, obj);
                    obj.setParentNodeId(schema.getNodeId());
                    obj.setNodeType(NodeType.Array);
                    array.add(obj);
                } else {
                    obj.setParentNodeId(schema.getNodeId());
                    obj.setNodeName(String.valueOf(entry.getValue()));
                    obj.setNodeType(NodeType.Key);
                    array.add(obj);
                }
            }
        }
    }

    private static String getTagName(String tag) {
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
