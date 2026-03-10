package cn.healthcaredaas.data.cloud.web.rest.annotation;

import cn.healthcaredaas.data.cloud.web.rest.configuration.RemoteUserAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**

 * @ClassName： EnableGlobalUser.java
 * @Author： chenpan
 * @Date：2024/12/16 17:06
 * @Modify：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableFeignClients
@Import({RemoteUserAutoConfiguration.class})
public @interface EnableGlobalUser {
}
