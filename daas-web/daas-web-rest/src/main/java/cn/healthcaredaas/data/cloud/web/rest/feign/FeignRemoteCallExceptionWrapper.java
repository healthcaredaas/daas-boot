package cn.healthcaredaas.data.cloud.web.rest.feign;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;

/**
 * <pre>Feign Fallback 错误统一封装器</pre>
 *
 * @ClassName： FeignRemoteCallExceptionWrapper.java
 * @Author： chenpan
 * @Date：2024/11/30 11:57
 * @Modify：
 */
public class FeignRemoteCallExceptionWrapper extends PlatformException {

    private RestResult<String> result;

    public FeignRemoteCallExceptionWrapper(RestResult<String> result) {
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, RestResult<String> result) {
        super(message);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, RestResult<String> result) {
        super(message, cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(Throwable cause, RestResult<String> result) {
        super(cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, RestResult<String> result) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.result = result;
    }

    @Override
    public RestResult<String> getResult() {
        return result;
    }
}
