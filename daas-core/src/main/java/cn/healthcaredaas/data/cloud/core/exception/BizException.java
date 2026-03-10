package cn.healthcaredaas.data.cloud.core.exception;

import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;

/**
 * <pre>业务异常</pre>
 *
 * @ClassName： BizException.java
 * @Author： chenpan
 * @Date：2024/12/15 10:52
 * @Modify：
 */
public class BizException extends PlatformException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public RestResult<String> getResult() {
        RestResult<String> result = RestResult.failure();
        Feedback feedback = getFeedback();
        result.code(feedback.getCode());
        result.message(getMessage());
        result.status(feedback.getStatus());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}
