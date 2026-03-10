package cn.healthcaredaas.data.cloud.data.exchange.exception;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.exchange.enums.ExchangeStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**

 * @ClassName： ExchangeExceptionHandler.java
 * @Author： chenpan
 * @Date：2024/9/26 14:58
 * @Modify：
 */
@Slf4j
@Component
public class ExchangeExceptionHandler {

    public RestResult<String> defaultErrorHandler(Exception e) {
        log.error("异常：", e);
        return fail(ExchangeStatus.SYSTEM_ERROR, e.getMessage());
    }

    public Object handleException(Exception e) {
        if (e instanceof NotNullException) {
            return fail(ExchangeStatus.FIELD_NOT_NULL, e.getMessage());
        }
        if (e instanceof NoDependsException) {
            //todo 放入重试队列
            log.debug("重试：{}, 主题： {}", ((NoDependsException) e).isRetry(), ((NoDependsException) e).getRetryTopic());
            return fail(ExchangeStatus.DEPENDENCY_FAILURE, e.getMessage());
        }
        return defaultErrorHandler(e);
    }

    public RestResult<String> fail(ExchangeStatus status, String msg) {
        return RestResult.failure(status.getMsg(), status.getCode(), null);
    }
}
