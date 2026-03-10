package cn.healthcaredaas.data.cloud.id.model;

import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author chenpan
 */
@TableName(value = "base_id")
@Data
@ToString(callSuper = true)
public class BaseId extends BaseEntity {

    @TableField(value = "biz_code")
    private String bizCode;

    @TableField(value = "next_value")
    private Integer nextValue;

    @TableField(value = "step")
    private Integer step;

    @TableField(value = "prefix")
    private String prefix;

}

