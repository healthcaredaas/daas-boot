package cn.healthcaredaas.data.cloud.audit.model;

import java.time.LocalDateTime;
import java.util.Date;

import cn.healthcaredaas.data.cloud.audit.enums.OperationType;
import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectBetweenColumn;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @ClassName： OperationLog.java
 * @Description: 操作日志
 * @Author： chenpan
 * @Date：2025-06-06 21:26:06
 * @Modify：
 */
@TableName(value = "audit_operation_log")
@Data
@ToString(callSuper = true)
@Schema(name = "audit_operation_log", description = "操作日志")
public class OperationLog extends BaseEntity {

    public OperationLog() {
        setSortBy("operationTime desc");
    }

    @Schema(description = "用户名")
    @TableField(value = "username")
    private String username;

    @Schema(description = "客户端ID")
    @TableField(value = "client_id")
    private String clientId;

    @Schema(description = "IP地址")
    @TableField(value = "operator_ip")
    private String operatorIp;

    @Schema(description = "操作时间")
    @TableField(value = "operation_time")
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @SelectBetweenColumn(position = SelectBetweenColumn.Position.START_AND_END, startValueColumn = "startTime", endValueColumn = "endTime")
    private LocalDateTime operationTime;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @TableField(exist = false)
    private LocalDateTime endTime;

    @Schema(description = "操作模块")
    @TableField(value = "module")
    private String module;

    @Schema(description = "操作类型")
    @TableField(value = "operation_type")
    private OperationType operationType;

    @Schema(description = "操作描述")
    @TableField(value = "operation_desc")
    private String operationDesc;

    @Schema(description = "请求方法")
    @TableField(value = "method")
    private String method;

    @Schema(description = "请求参数")
    @TableField(value = "request_params")
    private String requestParams;

    @Schema(description = "返回结果")
    @TableField(value = "result_params")
    private String resultParams;

    @Schema(description = "耗时（毫秒）")
    @TableField(value = "time")
    private Long time;

    @Schema(description = "操作状态")
    @TableField(value = "status")
    private SuccessStatusEnum status;

    @Schema(description = "错误信息")
    @TableField(value = "error_message")
    private String errorMessage;

    @Schema(description = "用户代理")
    @TableField(value = "user_agent")
    private String userAgent;

    @Schema(description = "请求URI")
    @TableField(value = "request_uri")
    private String requestUri;

    @Schema(description = "业务ID")
    @TableField(value = "business_id")
    private String businessId;

    @Schema(description = "业务类型")
    @TableField(value = "business_type")
    private String businessType;

}

