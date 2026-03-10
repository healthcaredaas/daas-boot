package cn.healthcaredaas.data.cloud.data.scheduler.powerjob.vo;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 操作powerjob的接口的参数
 */
@Data
@Schema(description = "操作powerjob的接口的参数")
public class TaskVo {

    @Schema(description = "任务ID")
    private String id;

    @Schema(description = "任务名称", required = true)
    @NotBlank(message = "任务名称 不能为空")
    private String jobName;

    @Schema(description = "任务参数", required = true)
    @NotBlank(message = "任务参数 不能为空")
    private JSONObject jobParams;

    @Schema(description = "任务处理器（执行器信息）")
    private String processorInfo;

    @Schema(description = "定时类型，时间表达式类型（CRON/API/FIX_RATE/FIX_DELAY）", required = true)
    @NotBlank(message = "定时类型 不能为空")
    private Integer timeExpressionType;

    @Schema(description = "定时时间", required = true)
    @NotBlank(message = "定时时间 不能为空")
    private String timeExpression;

    @Schema(description = "下次执行时间")
    private Long nextTriggerTime;

    @Schema(description = "状态，1 正常运行，2 停止（不再调度）")
    private Integer status;
}
