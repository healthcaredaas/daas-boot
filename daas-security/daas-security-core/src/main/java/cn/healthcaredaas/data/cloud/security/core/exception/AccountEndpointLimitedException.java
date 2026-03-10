package cn.healthcaredaas.data.cloud.security.core.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * <pre>登录端点限制异常</pre>
 *
 * @ClassName： AccountEndpointLimitedException.java
 * @Author： chenpan
 * @Date：2024/12/1 11:24
 * @Modify：
 */
public class AccountEndpointLimitedException extends AccountStatusException {

    public AccountEndpointLimitedException(String msg) {
        super(msg);
    }

    public AccountEndpointLimitedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}