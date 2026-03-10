package cn.healthcaredaas.data.cloud.core.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**

 * @ClassName： DaaSBootAutoConfiguration.java
 * @Author： chenpan
 * @Date：2024/12/1 14:40
 * @Modify：
 */
@Configuration
@ComponentScan(basePackages = {
        "cn.healthcaredaas.data.cloud"
})
@Slf4j
public class DaaSBootAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[DaaS] Auto Configure.");
    }
}
