package cn.healthcaredaas.data.cloud.security.core.exception;

import cn.healthcaredaas.data.cloud.core.exception.DaaSException;
import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import org.springframework.security.core.AuthenticationException;

/**
 * <pre>平台认证基础Exception</pre>
 *
 * @ClassName： PlatformAuthenticationException.java
 * @Author： chenpan
 * @Date：2024/12/1 11:25
 * @Modify：
 */
public class PlatformAuthenticationException extends AuthenticationException implements DaaSException {

    public PlatformAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PlatformAuthenticationException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return Feedback.ERROR;
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
