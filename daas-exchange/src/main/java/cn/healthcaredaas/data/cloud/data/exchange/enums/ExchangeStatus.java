package cn.healthcaredaas.data.cloud.data.exchange.enums;

import lombok.Getter;

/**

 * @ClassName： ExchangeStatus.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 13:55
 * @Modify：
 */
public enum ExchangeStatus {
    OK(200, "成功"),
    ADD_OK(201, "新增成功"),
    UPDATE_OK(202, "更新成功"),
    DEL_OK(203, "删除成功"),
    NOT_SUPPORTED(4000, "不支持的操作类型"),
    FIELD_NOT_NULL(4001, "字段不能为空"),
    DEPENDENCY_FAILURE(4002, "依赖数据不存在"),
    ALREADY_EXIST(4010, "数据已存在"),
    SYSTEM_ERROR(500, "系统异常");

    @Getter
    private final int code;

    @Getter
    private final String msg;

    ExchangeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
