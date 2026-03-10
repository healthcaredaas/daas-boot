package cn.healthcaredaas.data.cloud.data.dataformat.node;

import cn.healthcaredaas.data.cloud.core.enums.DataType;
import lombok.Data;

import java.util.List;

/**

 * @ClassName： NodeSchema.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/7 17:24
 * @Modify：
 */
@Data
public class NodeSchema {

    private Integer nodeId;

    private Integer parentNodeId;

    private NodeType nodeType;

    private String nodePath;

    private String nodeEnName;

    private String nodeName;

    private String nodeKey;

    private DataType dataType;

    private String fixedValue;

    private Boolean isNested;

    private String format;

    private Boolean notNull;

    private String description;

    private List<? extends NodeSchema> children;
}
