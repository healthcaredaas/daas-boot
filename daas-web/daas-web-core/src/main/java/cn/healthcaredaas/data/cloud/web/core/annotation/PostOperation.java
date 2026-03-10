package cn.healthcaredaas.data.cloud.web.core.annotation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**

 * @ClassName： PostOperation.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:59
 * @Modify：
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = {RequestMethod.POST})
@Operation
public @interface PostOperation {

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String name() default "";
}
