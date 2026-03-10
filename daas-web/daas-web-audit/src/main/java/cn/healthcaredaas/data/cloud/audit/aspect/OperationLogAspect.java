package cn.healthcaredaas.data.cloud.audit.aspect;

import cn.healthcaredaas.data.cloud.audit.annotation.OperationLog;
import cn.healthcaredaas.data.cloud.audit.event.OperationLogEvent;
import cn.healthcaredaas.data.cloud.audit.util.HttpLogUtils;
import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.context.UserContextHolder;
import cn.healthcaredaas.data.cloud.core.enums.SuccessStatusEnum;
import cn.healthcaredaas.data.cloud.web.core.annotation.Api;
import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import cn.healthcaredaas.data.cloud.web.core.utils.HeaderSecurityUtils;
import cn.healthcaredaas.data.cloud.web.core.utils.HttpUtils;
import cn.healthcaredaas.data.cloud.web.core.utils.SpringELUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName： OperationLogAspect.java
 * @Description: 操作日志切面
 * @Author： chenpan
 * @Date：2025-06-06 21:26:06
 * @Modify：
 */
@Aspect
@Slf4j
public class OperationLogAspect {

    @Value("${spring.application.name:}")
    private String appName;

    @Value("${spring.application.desc:}")
    private String appDesc;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private static final ThreadLocal<cn.healthcaredaas.data.cloud.audit.model.OperationLog> operationLogThreadLocal = new ThreadLocal<>();

    // Spring EL 表达式解析器
    private final ExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    
    @Pointcut("@annotation(cn.healthcaredaas.data.cloud.audit.annotation.OperationLog)")
    public void operationLogPointcut() {
    }

    @Before("operationLogPointcut() && @annotation(operationLog)")
    public void doBefore(JoinPoint joinPoint, OperationLog operationLog) {
        try {
            startTime.set(System.currentTimeMillis());

            cn.healthcaredaas.data.cloud.audit.model.OperationLog logEntity = new cn.healthcaredaas.data.cloud.audit.model.OperationLog();

            // 设置基本信息
            logEntity.setOperationTime(LocalDateTime.now());


            if(StrUtil.isEmpty(operationLog.module())){
                logEntity.setModule(StrUtil.emptyToDefault(appDesc, appName));
            }else {
                logEntity.setModule(operationLog.module());
            }
            logEntity.setOperationType(operationLog.type());
            logEntity.setOperationDesc(operationLog.desc());
            // 如果 operationLog.businessType() 为空，则尝试从类上的 @Api 注解获取
            if (StrUtil.isEmpty(operationLog.businessType())) {
                String defaultBusinessType = getDefaultBusinessType(joinPoint);
                if (defaultBusinessType != null) {
                    logEntity.setBusinessType(defaultBusinessType);
                }
            }else {
                logEntity.setBusinessType(parseSpelExpression(operationLog.businessType(), joinPoint));
            }

            if(StrUtil.isNotEmpty(operationLog.businessId())){
                logEntity.setBusinessId(parseSpelExpression(operationLog.businessId(), joinPoint));
            }

            // 获取请求信息
            HttpServletRequest request = HttpUtils.getRequest();
            logEntity.setOperatorIp(HttpUtils.getIP(request));
            logEntity.setUserAgent(HttpUtils.getAgent(request));
            logEntity.setRequestUri(request.getRequestURI());
            logEntity.setMethod(request.getMethod());

            CurrentUserInfo userInfo = UserContextHolder.getCurrentUserInfo();
            if(userInfo != null){
                logEntity.setUsername(userInfo.getUsername());
            }
            logEntity.setClientId(request.getHeader(SecurityConstants.CLIENT_HEADER));

            // 记录请求参数
            if (operationLog.includeArgs()) {
                logEntity.setRequestParams(JSON.toJSONString(HttpLogUtils.getParameters(request)));
            }

            operationLogThreadLocal.set(logEntity);
        }catch (Exception e){
            log.error("操作日志切面异常", e);
        }

    }

    @AfterReturning(pointcut = "operationLogPointcut() && @annotation(operationLog)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, OperationLog operationLog, Object result) {
        handleLog(operationLog, result, null);
    }

    @AfterThrowing(pointcut = "operationLogPointcut() && @annotation(operationLog)", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, OperationLog operationLog, Exception exception) {
        handleLog(operationLog, null, exception);
    }

    private void handleLog(OperationLog operationLogAnnotation, Object result, Exception exception) {
        try {
            cn.healthcaredaas.data.cloud.audit.model.OperationLog logEntity = operationLogThreadLocal.get();
            if (logEntity == null) {
                return;
            }

            Long start = startTime.get();
            if (start != null) {
                logEntity.setTime(System.currentTimeMillis() - start);
            }

            // 设置操作状态
            if (exception != null) {
                logEntity.setStatus(SuccessStatusEnum.FAIL);
                if (operationLogAnnotation.includeException()) {
                    logEntity.setErrorMessage(exception.getMessage());
                }
            } else {
                logEntity.setStatus(SuccessStatusEnum.SUCCESS);
                if (operationLogAnnotation.includeResult() && result != null) {
                    try {
                        logEntity.setResultParams(JSONUtil.toJsonStr(result));
                    } catch (Exception e) {
                        logEntity.setResultParams("结果序列化失败: " + e.getMessage());
                    }
                }
            }

            // 发布事件
            eventPublisher.publishEvent(new OperationLogEvent(logEntity));

        } catch (Exception e) {
            log.error("操作日志记录失败", e);
        } finally {
            // 清理ThreadLocal
            startTime.remove();
            operationLogThreadLocal.remove();
        }
    }

    /**
     * 解析 Spring EL 表达式
     * @param expression 表达式字符串
     * @param joinPoint 切点信息
     * @return 解析后的字符串
     */
    private String parseSpelExpression(String expression, JoinPoint joinPoint) {
       return SpringELUtils.parseElExpression(expression, joinPoint);
    }
    
    /**
     * 根据JoinPoint获取类上@Api注解的name属性
     */
    private String getDefaultBusinessType(JoinPoint joinPoint) {
        try {
            // 获取目标类
            Class<?> targetClass = joinPoint.getTarget().getClass();

            // 仅查找类上的 @Api 注解
            Api apiAnnotation = targetClass.getAnnotation(Api.class);
            if (apiAnnotation != null && !apiAnnotation.name().isEmpty()) {
                return apiAnnotation.name();
            }

            // Api注解不存在时，从swagger的@Tags获取
            Tags tagsAnnotation = targetClass.getAnnotation(Tags.class);
            if (tagsAnnotation != null && tagsAnnotation.value().length > 0){
                return tagsAnnotation.value()[0].name();
            }

            return null;
        } catch (Exception e) {
            log.debug("无法从类上的 @API 注解中获取 name 属性", e);
            return null;
        }
    }
}