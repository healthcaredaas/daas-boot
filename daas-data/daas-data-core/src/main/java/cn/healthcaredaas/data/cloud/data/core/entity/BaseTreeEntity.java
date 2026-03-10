package cn.healthcaredaas.data.cloud.data.core.entity;

import cn.healthcaredaas.data.cloud.data.core.utils.tree.TreeNode;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName： BaseTreeEntity.java
 * @Description: 树形实体基类
 * @Author： chenpan
 * @Date：2024/3/14 11:39
 * @Modify：
 */
@Data
public class BaseTreeEntity<T extends BaseTreeEntity> extends BaseEntity implements TreeNode {

    @TableField(value = "parent_id")
    @Schema(description = "父ID")
    private String parentId;

    @TableField(exist = false)
    private List<T> children;

    /**
     * 返回当前节点的父节点id
     *
     * @return 当前对象的父id
     */
    @Override
    public Object getParentNodeId() {
        return getParentId();
    }

    /**
     * 返回当前节点的唯一id
     *
     * @return 当前对象的唯一id
     */
    @Override
    public Object getNodeId() {
        return getId();
    }

    /**
     * 返回子节点列表, 必须提前初始化一个ArrayList
     *
     * @return 存储子节点的已实例化可变list容器
     */
    @Override
    public List<T> getChildNodes() {
        if(children == null){
            setChildren(new ArrayList<>());
        }
        return getChildren();
    }
}