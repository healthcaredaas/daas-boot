package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.healthcaredaas.data.cloud.core.exception.BizException;
import cn.healthcaredaas.data.cloud.data.dataformat.node.NodeSchema;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

public class NodeTreeUtils {
    public static <T extends NodeSchema> T getNodeTree(List<T> nodeList) {
        T rootNode = null;
        if (CollectionUtil.isNotEmpty(nodeList)) {
            Multimap<Integer, T> multimap = ArrayListMultimap.create();

            for (T node : nodeList) {
                if (node.getParentNodeId() == null
                        || node.getParentNodeId() == 0) {
                    rootNode = node;
                    continue;
                }
                multimap.put(node.getParentNodeId(), node);
            }
            if (rootNode != null) {
                extractNodeTree(rootNode, multimap);
            }
        } else {
            throw new BizException("缺少字段配置信息！");
        }
        return rootNode;
    }

    private static <T extends NodeSchema> void extractNodeTree(T node, Multimap<Integer, T> multimap) {
        List<T> children = new ArrayList<>(multimap.get(node.getNodeId()));
        node.setChildren(children);
        for (T child : children) {
            extractNodeTree(child, multimap);
        }
    }
}
