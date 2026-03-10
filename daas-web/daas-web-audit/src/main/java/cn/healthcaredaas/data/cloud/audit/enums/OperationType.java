package cn.healthcaredaas.data.cloud.audit.enums;

import cn.healthcaredaas.data.cloud.core.enums.IBaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @Description: TOOD
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/8 12:37
 * @Modify：
 */
public enum OperationType implements IBaseEnum<String> {
    CREATE("create", "创建"),
    UPDATE("update", "更新"),
    DELETE("delete", "删除"),
    QUERY("query", "查询"),
    EXPORT("export", "导出"),
    IMPORT("import", "导入"),
    OTHER("other", "其他");

    @Getter
    @EnumValue
    @JsonValue
    private String key;

    @Getter
    private String value;

    OperationType(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
