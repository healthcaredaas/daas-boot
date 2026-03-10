package cn.healthcaredaas.data.cloud.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**

 * @ClassName： DataType.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:28
 * @Modify：
 */
public enum DataType {

    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp"),
    DATETIME("datetime"),
    STRING("string"),
    NUMBER("number");

    @EnumValue
    @JsonValue
    @Getter
    public String type;

    DataType(String type) {
        this.type = type;
    }
}
