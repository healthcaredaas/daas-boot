package cn.healthcaredaas.data.cloud.core.enums;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**

 * @ClassName： EnableStatus.java
 * @Author： chenpan
 * @Date：2024/10/21 10:31
 * @Modify：
 */
@Getter
public enum EnableStatusEnum {

    /**
     * 启用
     */
    ENABLE(true, "启用"),
    /**
     * 禁用
     */
    DISABLE(false, "禁用");

    @EnumValue
    @JsonValue
    private final Boolean code;

    private final String value;

    @JsonCreator
    public static EnableStatusEnum of(Boolean b) {
        return b ? ENABLE : DISABLE;
    }

    public static EnableStatusEnum of(String b) {
        return BooleanUtil.toBoolean(b) ? ENABLE : DISABLE;
    }

    EnableStatusEnum(Boolean code, String value) {
        this.code = code;
        this.value = value;
    }
}
