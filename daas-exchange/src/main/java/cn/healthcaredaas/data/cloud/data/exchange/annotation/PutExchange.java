package cn.healthcaredaas.data.cloud.data.exchange.annotation;

import cn.healthcaredaas.data.cloud.data.exchange.enums.ExchangeType;
import cn.healthcaredaas.data.cloud.web.core.annotation.PutOperation;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**

 * @ClassName： PutExchangeOperation.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:59
 * @Modify：
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PutOperation
public @interface PutExchange {

    @AliasFor(annotation = PutOperation.class, attribute = "value")
    String[] value() default {};

    @AliasFor(annotation = PutOperation.class, attribute = "name")
    String name() default "";

    ExchangeType type() default ExchangeType.UPDATE;

    String code() default "";
}
