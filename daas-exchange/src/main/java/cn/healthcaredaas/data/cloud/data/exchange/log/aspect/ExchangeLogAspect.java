package cn.healthcaredaas.data.cloud.data.exchange.log.aspect;

import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import cn.healthcaredaas.data.cloud.data.exchange.log.service.IExchangeApiLogService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.healthcaredaas.data.cloud.data.exchange.annotation.DeleteExchange;
import cn.healthcaredaas.data.cloud.data.exchange.annotation.GetExchange;
import cn.healthcaredaas.data.cloud.data.exchange.annotation.PostExchange;
import cn.healthcaredaas.data.cloud.data.exchange.annotation.PutExchange;
import cn.healthcaredaas.data.cloud.data.exchange.exception.ExchangeExceptionHandler;
import cn.healthcaredaas.data.cloud.data.exchange.log.ExchangeLog;
import cn.healthcaredaas.data.cloud.data.exchange.log.model.ExchangeApiLog;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName： ExchangeLogAspect.java
 * @Author： chenpan
 * @Date：2024/9/26 14:56
 * @Modify：
 */
@Aspect
@Slf4j
@Component
public class ExchangeLogAspect {

    @Autowired
    private ExchangeExceptionHandler exceptionHandler;

    @Autowired
    private IExchangeApiLogService exchangeApiLogService;

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private void pre() {
        startTime.set(System.currentTimeMillis());
    }

    @Before(value = "@annotation(operation)")
    public void logBefore(GetExchange operation) {
        pre();
    }

    @Before(value = "@annotation(exchangeOperation)")
    public void logBefore(PostExchange exchangeOperation) {
        pre();
    }

    @Before(value = "@annotation(operation)")
    public void logBefore(PutExchange operation) {
        pre();
    }

    @Before(value = "@annotation(operation)")
    public void logBefore(DeleteExchange operation) {
        pre();
    }

    @AfterReturning(value = "@annotation(operation)", returning = "result")
    public void afterReturning(JoinPoint point, GetExchange operation, Object result) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        saveLog(point, exchangeLog, result, null);
    }

    @AfterReturning(value = "@annotation(operation)", returning = "result")
    public void afterReturning(JoinPoint point, PostExchange operation, Object result) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        saveLog(point, exchangeLog, result, null);
    }

    @AfterReturning(value = "@annotation(operation)", returning = "result")
    public void afterReturning(JoinPoint point, PutExchange operation, Object result) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        saveLog(point, exchangeLog, result, null);
    }

    @AfterReturning(value = "@annotation(operation)", returning = "result")
    public void afterReturning(JoinPoint point, DeleteExchange operation, Object result) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        saveLog(point, exchangeLog, result, null);
    }

    @AfterThrowing(value = "@annotation(operation)", throwing = "e")
    public void afterThrowing(JoinPoint point, GetExchange operation, Exception e) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        exceptionHandler.handleException(e);
        saveLog(point, exchangeLog, null, e);
    }

    @AfterThrowing(value = "@annotation(operation)", throwing = "e")
    public void afterThrowing(JoinPoint point, PostExchange operation, Exception e) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        exceptionHandler.handleException(e);
        saveLog(point, exchangeLog, null, e);
    }

    @AfterThrowing(value = "@annotation(operation)", throwing = "e")
    public void afterThrowing(JoinPoint point, PutExchange operation, Exception e) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        exceptionHandler.handleException(e);
        saveLog(point, exchangeLog, null, e);
    }

    @AfterThrowing(value = "@annotation(operation)", throwing = "e")
    public void afterThrowing(JoinPoint point, DeleteExchange operation, Exception e) {
        ExchangeLog exchangeLog = new ExchangeLog(operation.code(), operation.name(), operation.type());
        exceptionHandler.handleException(e);
        saveLog(point, exchangeLog, null, e);
    }

    private void saveLog(JoinPoint point, ExchangeLog exchangeLog, Object result, Exception e) {
        ExchangeApiLog apiLog = new ExchangeApiLog();
        try {
            long endTime = System.currentTimeMillis();
            apiLog.setExchangeType(exchangeLog.getType());
            apiLog.setServiceCode(exchangeLog.getCode());
            apiLog.setServiceName(exchangeLog.getName());

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            Map<String, String[]> parameterMap = request.getParameterMap();
            if (MapUtil.isEmpty(parameterMap)) {
                apiLog.setRequestContent(JSON.toJSONString(point.getArgs()));
            } else {
                apiLog.setRequestContent(JSON.toJSONString(parameterMap));
            }
            apiLog.setUrl(request.getRequestURI());
            if (e == null) {
                apiLog.setResponse(JSON.toJSONString(result));
                apiLog.setStatus(SuccessStatusEnum.SUCCESS);
            } else {
                apiLog.setResponse(e.getMessage());
                apiLog.setStatus(SuccessStatusEnum.FAIL);
            }
            apiLog.setStartTime(LocalDateTimeUtil.of(startTime.get()));
            apiLog.setEndTime(LocalDateTimeUtil.of(endTime));
            apiLog.setElapsedTime(endTime - startTime.get());
            exchangeApiLogService.saveLog(apiLog);
        } catch (Exception ex) {
            log.error("记录调用日志异常：{}", ex.getMessage());
        } finally {
            startTime.remove();
        }
    }
}
