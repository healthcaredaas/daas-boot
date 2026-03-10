package cn.healthcaredaas.data.cloud.data.exchange.log.model;

import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import cn.healthcaredaas.data.cloud.data.core.annotation.EnableSelectOption;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectBetweenColumn;
import cn.healthcaredaas.data.cloud.data.exchange.enums.ExchangeType;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @ClassName： ExchangeApiLog.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:26
 * @Modify：
 */
@Data
@EnableSelectOption
@ToString(callSuper = true)
@TableName(value = "log_exchange_api")
public class ExchangeApiLog extends BaseEntity {

    @TableField(value = "message_id")
    @Schema(description = "消息id")
    private String messageId;

    @TableField(value = "service_code")
    @Schema(description = "服务编码")
    private String serviceCode;

    @TableField(value = "service_name")
    @Schema(description = "服务名称")
    private String serviceName;

    @TableField(value = "exchange_type")
    @Schema(description = "交互类型")
    private ExchangeType exchangeType;

    @TableField(value = "url")
    @Schema(description = "请求地址")
    private String url;

    @TableField(value = "retry")
    @Schema(description = "是否重试")
    private Boolean retry;

    @TableField(value = "retry_times")
    @Schema(description = "重试次数")
    private Integer retryTimes;

    @TableField(value = "request_content")
    @Schema(description = "请求内容")
    private String requestContent;

    @TableField(value = "response")
    @Schema(description = "返回内容")
    private String response;

    @TableField(value = "start_time")
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @Schema(description = "请求开始时间")
    @SelectBetweenColumn(position = SelectBetweenColumn.Position.START_AND_END, startValueColumn = "startTime", endValueColumn = "endTime")
    private LocalDateTime startTime;

    @TableField(value = "end_time")
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    @Schema(description = "处理完成时间")
    private LocalDateTime endTime;

    @TableField(value = "elapsed_time")
    @Schema(description = "用时（ms）")
    private Long elapsedTime;

    @TableField(value = "status")
    @Schema(description = "请求成功状态")
    private SuccessStatusEnum status;
}
