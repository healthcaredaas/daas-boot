package cn.healthcaredaas.data.cloud.core.rest;

import cn.healthcaredaas.data.cloud.core.enums.ResultErrorCodes;
import cn.healthcaredaas.data.cloud.core.utils.DateFormatterConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

import java.io.Serializable;
import java.util.Date;

/**

 * @ClassName： R.java
 * @Author： chenpan
 * @Date：2024/11/28 15:12
 * @Modify：
 */
@Schema(title = "统一响应返回实体", description = "所有Rest接口统一返回的实体定义")
@Data
public class RestResult<T> implements Serializable {

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "自定义响应编码")
    private int code = 0;

    @Schema(description = "响应返回信息")
    private String message;

    @Schema(description = "请求路径")
    private String path;

    @Schema(description = "响应返回数据")
    private T data;

    @Schema(description = "http状态码")
    private int status;

    @Schema(description = "链路 TraceId")
    private String traceId = TraceContext.traceId();

    @Schema(description = "响应时间戳", pattern = DateFormatterConstant.DATETIME_FORMAT)
    @JsonFormat(pattern = DateFormatterConstant.DATETIME_FORMAT, timezone = "GMT+8")
    private Date timestamp = new Date();

    @Schema(description = "校验错误信息")
    @JsonIgnore
    private Error error = new Error();

    public RestResult<T> success(Boolean success) {
        this.success = success;
        return this;
    }

    public RestResult<T> code(int code) {
        this.code = code;
        return this;
    }

    public RestResult<T> message(String message) {
        this.message = message;
        return this;
    }

    public RestResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public RestResult<T> path(String path) {
        this.path = path;
        return this;
    }

    public RestResult<T> type(ResultErrorCodes resultErrorCodes) {
        this.code = resultErrorCodes.getCode();
        this.message = resultErrorCodes.getMessage();
        return this;
    }

    public RestResult<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public RestResult<T> status(int httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public RestResult<T> stackTrace(StackTraceElement[] stackTrace) {
        this.error.setStackTrace(stackTrace);
        return this;
    }

    public RestResult<T> detail(String detail) {
        this.error.setDetail(detail);
        return this;
    }

    public RestResult<T> validation(String message, String code, String field) {
        this.error.setMessage(message);
        this.error.setCode(code);
        this.error.setField(field);
        return this;
    }

    private static <T> RestResult<T> create(Boolean success, String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        RestResult<T> RestResult = new RestResult<>();
        if (StringUtils.isNotBlank(message)) {
            RestResult.message(message);
        }

        if (StringUtils.isNotBlank(detail)) {
            RestResult.detail(detail);
        }
        RestResult.success(success);
        RestResult.code(code);
        RestResult.status(status);

        if (ObjectUtils.isNotEmpty(data)) {
            RestResult.data(data);
        }

        if (ArrayUtils.isNotEmpty(stackTrace)) {
            RestResult.stackTrace(stackTrace);
        }

        return RestResult;
    }

    public static <T> RestResult<T> success(String message, int code, int status, T data) {
        return create(true, message, null, code, status, data, null);
    }

    public static <T> RestResult<T> success(String message, int code, T data) {
        return success(message, code, HttpStatus.SC_OK, data);
    }

    public static <T> RestResult<T> success(ResultErrorCodes resultErrorCodes, T data) {
        return success(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
    }

    public static <T> RestResult<T> success(Feedback feedback, T data) {
        return success(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
    }

    public static <T> RestResult<T> success(String message, T data) {
        return success(message, Feedback.OK.getCode(), data);
    }

    public static <T> RestResult<T> success(String message) {
        return success(message, null);
    }

    public static <T> RestResult<T> success() {
        return success("操作成功！");
    }

    public static <T> RestResult<T> content(T data) {
        return success("操作成功！", data);
    }

    public static <T> RestResult<T> failure(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
        return create(false, message, detail, code, status, data, stackTrace);
    }

    public static <T> RestResult<T> failure(String message, String detail, int code, int status, T data) {
        return failure(message, detail, code, status, data, null);
    }

    public static <T> RestResult<T> failure(String message, int code, int status, T data) {
        return failure(message, message, code, status, data);
    }

    public static <T> RestResult<T> failure(String message, String detail, int code, T data) {
        return failure(message, detail, code, HttpStatus.SC_INTERNAL_SERVER_ERROR, data);
    }

    public static <T> RestResult<T> failure(String message, int code, T data) {
        return failure(message, message, code, data);
    }

    public static <T> RestResult<T> failure(ResultErrorCodes resultErrorCodes, T data) {
        return failure(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
    }

    public static <T> RestResult<T> failure(Feedback feedback, T data) {
        return failure(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
    }

    public static <T> RestResult<T> failure(String message, T data) {
        return failure(message, Feedback.ERROR.getCode(), data);
    }

    public static <T> RestResult<T> failure(String message) {
        return failure(message, null);
    }

    public static <T> RestResult<T> failure() {
        return failure("操作失败！");
    }

    public static <T> RestResult<T> empty(String message, int code, int status) {
        return create(true, message, null, code, status, null, null);
    }

    public static <T> RestResult<T> empty(String message, int code) {
        return empty(message, code, Feedback.NO_CONTENT.getStatus());
    }

    public static <T> RestResult<T> empty(Feedback feedback) {
        return empty(feedback.getMessage(), feedback.getCode(), feedback.getStatus());
    }

    public static <T> RestResult<T> empty(ResultErrorCodes resultErrorCodes) {
        return empty(resultErrorCodes.getMessage(), resultErrorCodes.getCode());
    }

    public static <T> RestResult<T> empty(String message) {
        return empty(message, Feedback.NO_CONTENT.getCode());
    }

    public static <T> RestResult<T> empty() {
        return empty("未查询到相关内容！");
    }
}
