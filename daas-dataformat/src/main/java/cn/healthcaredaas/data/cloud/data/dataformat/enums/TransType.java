package cn.healthcaredaas.data.cloud.data.dataformat.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**

 * @ClassName： TransType.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/30 15:39
 * @Modify：
 */
public enum TransType {

    ToJSON("toJson"),
    ToXML("toXml"),
    Node("node"),
    Groovy("groovy");

    @EnumValue
    @Getter
    @JsonValue
    public final String code;

    TransType(String code) {
        this.code = code;
    }
}
