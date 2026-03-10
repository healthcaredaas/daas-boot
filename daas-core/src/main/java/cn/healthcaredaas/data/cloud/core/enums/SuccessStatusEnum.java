package cn.healthcaredaas.data.cloud.core.enums;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**

 * @ClassName： SuccessStatusEnum.java
 * @Author： chenpan
 * @Date：2024/10/21 10:31
 * @Modify：
 */
@Getter
public enum SuccessStatusEnum {

    /**
     * 成功
     */
    SUCCESS(true, "成功"),
    /**
     * 失败
     */
    FAIL(false, "失败");


    @EnumValue
    @JsonValue
    private final Boolean code;

    private final String value;

    @JsonCreator
    public static SuccessStatusEnum of(Boolean b) {
        return b ? SUCCESS : FAIL;
    }

    public static SuccessStatusEnum of(String b) {
        return BooleanUtil.toBoolean(b) ? SUCCESS : FAIL;
    }

    SuccessStatusEnum(Boolean code, String value) {
        this.code = code;
        this.value = value;
    }
}
