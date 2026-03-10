package cn.healthcaredaas.data.cloud.security.core.exception;

import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import org.apache.http.HttpStatus;

/**
 * <pre>非法加密Key异常</pre>
 *
 * @ClassName： IllegalSymmetricKeyException.java
 * @Author： chenpan
 * @Date：2024/12/1 11:27
 * @Modify：
 */
public class IllegalSymmetricKeyException extends PlatformAuthenticationException {

    public IllegalSymmetricKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalSymmetricKeyException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return new Feedback(50103, "静态AES加密算法KEY非法", HttpStatus.SC_NOT_IMPLEMENTED);
    }
}