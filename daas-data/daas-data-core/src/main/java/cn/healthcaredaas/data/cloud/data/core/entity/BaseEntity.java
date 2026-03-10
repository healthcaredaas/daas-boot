package cn.healthcaredaas.data.cloud.data.core.entity;

import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**

 * @ClassName： BaseEntity.java
 * @Author： chenpan
 * @Date：2024/11/28 16:19
 * @Modify：
 */
@Data
public abstract class BaseEntity extends AbstractEntity {

    public static final String ID_COLUMN = "id";

    @TableId(value = "id")
    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "创建人", hidden = true)
    @TableField(value = "create_by")
    private String createBy;

    @Schema(description = "创建时间", hidden = true)
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    //@JsonIgnore
    private LocalDateTime createTime;

    @Schema(description = "更改次数", hidden = true)
    @TableField(value = "update_count", update = "%s+1", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Integer updateCount;

    @Schema(description = "更新人", hidden = true)
    @TableField(value = "update_by")
    @JsonIgnore
    private String updateBy;

    @Schema(description = "更新时间", hidden = true)
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;

    @Schema(description = "删除标识", hidden = true)
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @JsonIgnore
    private String deleteFlag;

    @Schema(description = "删除时间", hidden = true)
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @TableField(value = "delete_time")
    @JsonIgnore
    private LocalDateTime deleteTime;

    @Schema(description = "排序字段")
    @TableField(exist = false)
    @JsonIgnore
    private String[] sortBy;

    public void setSortBy(String... sorts) {
        this.sortBy = sorts;
    }
}
