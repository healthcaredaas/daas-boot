package cn.healthcaredaas.data.cloud.data.core.utils.tree;

import jakarta.annotation.Nonnull;
import java.beans.Transient;
import java.util.*;

/**

 * @ClassName： TreeNode.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/14 11:47
 * @Modify：
 */
public interface TreeNode {

    /**
     * 将列表转换为树
     *
     * @param source 源列表
     * @param <E>    TreeNode的实现类
     * @return 树结构的列表
     */
    @Nonnull
    static <E extends TreeNode> List<E> toTree(@Nonnull Collection<E> source) {
        int size = source.size();
        if (size < 2) {
            return new ArrayList<>(source);
        }
        List<E> result = new ArrayList<>();
        Map<Object, E> sourceMap = new HashMap<>(size);
        for (E e : source) {
            if (e != null) {
                Object nodeId = e.getNodeId();
                sourceMap.put(nodeId, e);
            }
        }
        for (E e : source) {
            if (e == null) {
                continue;
            }
            Object parentNodeId = e.getParentNodeId();
            E node = null;
            if (parentNodeId != null) {
                node = sourceMap.get(parentNodeId);
            }
            if (node == null) {
                result.add(e);
            } else {
                @SuppressWarnings("rawtypes")
                List childNodes = node.getChildNodes();
                childNodes.add(e);
            }
        }
        return result;
    }

    /**
     * 返回当前节点的父节点id
     *
     * @return 当前对象的父id
     */
    @Transient
    Object getParentNodeId();

    /**
     * 返回当前节点的唯一id
     *
     * @return 当前对象的唯一id
     */
    @Transient
    Object getNodeId();

    /**
     * 返回子节点列表, 必须提前初始化一个ArrayList
     *
     * @return 存储子节点的已实例化可变list容器
     */
    @Transient
    List<? extends TreeNode> getChildNodes();
}
