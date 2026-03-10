package cn.healthcaredaas.data.cloud.data.exchange.exception;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;

/**

 * @ClassName： NoDependsException.java
 * @Author： chenpan
 * @Date：2024/9/26 14:23
 * @Modify：
 */
public class NotNullException extends PlatformException {

    public NotNullException() {

    }

    public NotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotNullException(String message) {
        super(message);
    }

    public NotNullException(Throwable cause) {
        super(cause);
    }
}
