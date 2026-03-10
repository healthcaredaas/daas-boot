package cn.healthcaredaas.data.cloud.core.feign;

import java.lang.annotation.*;

/**
 * <pre>Feign 内部调用标记注解</pre>
 *
 * @ClassName： Inner.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/13 16:33
 * @Modify：
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {
}
