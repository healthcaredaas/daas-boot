package cn.healthcaredaas.data.cloud.audit.model;

import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import cn.healthcaredaas.data.cloud.data.core.annotation.EnableSelectOption;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectBetweenColumn;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName： LoginLog.java
 * @Description: 登录日志
 * @Author： chenpan
 * @Date：2025-06-06 21:26:00
 * @Modify：
 */
@TableName(value = "audit_login_log")
@Data
@ToString(callSuper = true)
@Schema(name = "audit_login_log", description = "登录日志")
@EnableSelectOption
public class LoginLog extends BaseEntity {

    public LoginLog() {
        setSortBy("createTime desc");
    }

    @TableField(value = "create_time")
    @DateTimeFormat(pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @SelectBetweenColumn(position = SelectBetweenColumn.Position.START_AND_END, startValueColumn = "startTime", endValueColumn = "endTime")
    private LocalDateTime createTime;

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

    @Schema(description = "用户名")
    @TableField(value = "username")
    private String username;

    @Schema(description = "客户端ID")
    @TableField(value = "client_id")
    private String clientId;

    @Schema(description = "登录状态")
    @TableField(value = "status")
    private SuccessStatusEnum status;

    @Schema(description = "失败原因")
    @TableField(value = "failure_reason")
    private String failureReason;

    @Schema(description = "IP地址")
    @TableField(value = "ip_address")
    private String ipAddress;

    @Schema(description = "用户代理")
    @TableField(value = "user_agent")
    private String userAgent;

    @Schema(description = "请求URI")
    @TableField(value = "request_uri")
    private String requestUri;

}

