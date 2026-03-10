package cn.healthcaredaas.data.cloud.core.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**

 * @ClassName： Error.java
 * @Author： chenpan
 * @Date：2024/11/28 15:18
 * @Modify：
 */
@Data
@Schema(title = "响应错误详情", description = "为兼容Validation而增加的Validation错误信息实体")
public class Error {

    @Schema(description = "Exception完整信息", type = "string")
    private String detail;

    @Schema(description = "额外的错误信息，目前主要是Validation的Message")
    private String message;

    @Schema(description = "额外的错误代码，目前主要是Validation的Code")
    private String code;

    @Schema(description = "额外的错误字段，目前主要是Validation的Field")
    private String field;

    @Schema(description = "错误堆栈信息")
    private StackTraceElement[] stackTrace;
}
