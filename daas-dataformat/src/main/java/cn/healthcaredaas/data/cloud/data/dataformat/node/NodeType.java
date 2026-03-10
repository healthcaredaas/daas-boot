package cn.healthcaredaas.data.cloud.data.dataformat.node;

/**

 * @ClassName： NodeType.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/7 17:39
 * @Modify：
 */
public enum NodeType {
    Array, Object, Key, Comment, Element, Attribute;

    public static NodeType of(String nodeTypeName) {
        for (NodeType nodeType : values()) {
            if (nodeType.name().equalsIgnoreCase(nodeTypeName)) {
                return nodeType;
            }
        }
        return null;
    }
}
