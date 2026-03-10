package cn.healthcaredaas.data.cloud.core.exception;

import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;

/**
 * <pre>自定义异常抽象类</pre>
 *
 * @ClassName： AbstractDaaSException.java
 * @Author： chenpan
 * @Date：2024/11/28 15:44
 * @Modify：
 */
public abstract class AbstractDaaSException extends RuntimeException implements DaaSException {

    public AbstractDaaSException() {
        super();
    }

    public AbstractDaaSException(String message) {
        super(message);
    }

    public AbstractDaaSException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractDaaSException(Throwable cause) {
        super(cause);
    }

    protected AbstractDaaSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public RestResult<String> getResult() {
        RestResult<String> result = RestResult.failure();
        Feedback feedback = getFeedback();
        result.code(feedback.getCode());
        result.message(feedback.getMessage());
        result.status(feedback.getStatus());
        result.stackTrace(super.getStackTrace());
        result.detail(super.getMessage());
        return result;
    }
}
