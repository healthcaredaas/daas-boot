package cn.healthcaredaas.data.cloud.core.exception;

import cn.healthcaredaas.data.cloud.core.rest.Feedback;

/**
 * <pre>平台基础异常</pre>
 *
 * @ClassName： PlatformException.java
 * @Author： chenpan
 * @Date：2024/11/29 13:33
 * @Modify：
 */
public class PlatformException extends AbstractDaaSException {
    public PlatformException() {
        super();
    }

    public PlatformException(String message) {
        super(message);
    }

    public PlatformException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlatformException(Throwable cause) {
        super(cause);
    }

    protected PlatformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return Feedback.ERROR;
    }
}
