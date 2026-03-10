package cn.healthcaredaas.data.cloud.web.core.exception;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import cn.healthcaredaas.data.cloud.web.core.constants.WebErrorCode;
import org.apache.http.HttpStatus;

/**

 * @ClassName： FeignDecodeIOException.java
 * @Author： chenpan
 * @Date：2024/11/30 11:56
 * @Modify：
 */
public class FeignDecodeIOException extends PlatformException {

    public FeignDecodeIOException() {
        super();
    }

    public FeignDecodeIOException(String message) {
        super(message);
    }

    public FeignDecodeIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignDecodeIOException(Throwable cause) {
        super(cause);
    }

    protected FeignDecodeIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return new Feedback(WebErrorCode.FEIGN_DECODER_IO_EXCEPTION, "Feign 解析 Fallback 错误信息出错", HttpStatus.SC_SERVICE_UNAVAILABLE);
    }
}
