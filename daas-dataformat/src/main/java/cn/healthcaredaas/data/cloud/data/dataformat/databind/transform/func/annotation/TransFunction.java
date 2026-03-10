package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation;

import java.lang.annotation.*;

/**

 * @ClassName： TransFunction.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 10:17
 * @Modify：
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransFunction {

    String code() default "";

    String name() default "";

    String description() default "";
}
