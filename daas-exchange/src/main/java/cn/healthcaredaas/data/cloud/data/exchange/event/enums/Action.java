package cn.healthcaredaas.data.cloud.data.exchange.event.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**

 * @ClassName： Action.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:40
 * @Modify：
 */
public enum Action {

    // 新增
    ADD("0"),
    // 更新
    UPDATE("1"),
    // 删除
    DELETE("2");

    @Getter
    @JsonValue
    @EnumValue
    private String code;

    Action(String code) {
        this.code = code;
    }
}
