package cn.healthcaredaas.data.cloud.data.exchange.annotation;

import cn.healthcaredaas.data.cloud.web.core.annotation.Api;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**

 * @ClassName： Exchanger.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:45
 * @Modify：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Api
public @interface Exchanger {

    @AliasFor(annotation = Api.class, attribute = "value")
    String[] value() default {};

    @AliasFor(annotation = Api.class, attribute = "name")
    String name() default "";
}
