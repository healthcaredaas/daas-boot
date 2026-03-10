package cn.healthcaredaas.data.cloud.core.rest;

import cn.healthcaredaas.data.cloud.core.enums.ResultErrorCodes;
import lombok.Data;
import org.apache.http.HttpStatus;

/**

 * @ClassName： Feedback.java
 * @Author： chenpan
 * @Date：2024/11/28 15:24
 * @Modify：
 */
@Data
public class Feedback {

    public static final Feedback OK = new Feedback(20000, "成功", HttpStatus.SC_OK);
    public static final Feedback NO_CONTENT = new Feedback(20400, "无内容", HttpStatus.SC_NO_CONTENT);
    public static final Feedback ERROR = new Feedback(50000, "服务器内部错误", HttpStatus.SC_INTERNAL_SERVER_ERROR);

    /**
     * 自定义错误代码
     */
    private final int code;

    /**
     * 用户友好的错误信息
     */
    private final String message;

    /**
     * 对应 Http 请求状态码
     */
    private final int status;

    public Feedback(ResultErrorCodes resultErrorCodes, int status) {
        this(resultErrorCodes.getCode(), resultErrorCodes.getMessage(), status);
    }

    public Feedback(int code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
