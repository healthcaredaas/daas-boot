package cn.healthcaredaas.data.cloud.web.core.enums;

import cn.healthcaredaas.data.cloud.core.enums.IBaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>权限资源类型</pre>
 *
 * @ClassName： ResourceType.java
 * @Author： chenpan
 * @Date：2024/11/30 11:25
 * @Modify：
 */
@Schema(description = "权限类型")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ResourceType implements IBaseEnum<String> {

    API("0", "API/操作"),
    MENU("1", "菜单"),
    PAGE("2", "页面"),
    TAB("3", "页签"),
    OTHER("9", "其他");

    @Schema(description = "枚举值")
    @EnumValue
    @JsonValue
    private final String key;

    @Schema(description = "文字")
    private final String value;

    private static final Map<String, ResourceType> codeMap = new HashMap<>();
    private static final List<Map<String, Object>> toJsonStruct = new ArrayList<>();

    static {
        for (ResourceType resourceType : ResourceType.values()) {
            codeMap.put(resourceType.getKey(), resourceType);
            Map<String, Object> map = new HashMap<>(3);
            map.put("value", resourceType.getKey());
            map.put("key", resourceType.name());
            map.put("lable", resourceType.getValue());
            toJsonStruct.add(map);
        }
    }

    ResourceType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static ResourceType getResourceType(String key) {
        return codeMap.get(key);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return toJsonStruct;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ResourceType of(String key) {
        return IBaseEnum.of(ResourceType.class, key);
    }
}
