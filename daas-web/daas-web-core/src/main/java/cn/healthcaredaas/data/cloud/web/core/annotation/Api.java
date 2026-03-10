package cn.healthcaredaas.data.cloud.web.core.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**

 * @ClassName： Api.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:45
 * @Modify：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
@Tag(name = "")
@Inherited
public @interface Api {

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    @AliasFor(annotation = Tag.class, attribute = "name")
    String name() default "";
}
