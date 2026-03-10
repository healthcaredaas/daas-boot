package cn.healthcaredaas.data.cloud.audit.annotation;

import cn.healthcaredaas.data.cloud.audit.enums.OperationType;

import java.lang.annotation.*;

/**
 * @ClassName： OperationLog.java
 * @Description: 操作日志注解
 * @Author： chenpan
 * @Date：2025-06-06 21:26:06
 * @Modify：
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作类型
     */
    OperationType type();

    /**
     * 操作描述
     */
    String desc() default "";

    /**
     * 业务类型
     */
    String businessType() default "";

    String businessId() default "";

    /**
     * 是否记录请求参数
     */
    boolean includeArgs() default true;

    /**
     * 是否记录返回结果
     */
    boolean includeResult() default true;

    /**
     * 是否记录异常信息
     */
    boolean includeException() default true;
}