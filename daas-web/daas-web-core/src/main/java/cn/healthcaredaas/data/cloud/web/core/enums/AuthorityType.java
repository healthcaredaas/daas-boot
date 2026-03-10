package cn.healthcaredaas.data.cloud.web.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * <pre>权限类型</pre>
 *
 * @ClassName： AuthorityType.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/4/12 14:17
 * @Modify：
 */
@Getter
public enum AuthorityType {

    APPLICATION("0", "应用"),
    RESOURCE("1", "系统资源");

    @Schema(description = "枚举值")
    @EnumValue
    @JsonValue
    private final String code;

    @Schema(description = "文字")
    private final String label;

    AuthorityType(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
