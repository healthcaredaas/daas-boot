package cn.healthcaredaas.data.cloud.web.rest.configuration;

import cn.healthcaredaas.data.cloud.core.yaml.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**

 * @ClassName： WebRestProperties.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/1 15:55
 * @Modify：
 */
@Configuration
@PropertySource(value = "classpath:/config/application-rest.yaml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class WebRestProperties {
}
