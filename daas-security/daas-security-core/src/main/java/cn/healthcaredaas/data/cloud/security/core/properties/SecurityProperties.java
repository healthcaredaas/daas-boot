package cn.healthcaredaas.data.cloud.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**

 * @ClassName： SecurityProperties.java
 * @Author： chenpan
 * @Date：2024/12/1 13:58
 * @Modify：
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "daas.security")
public class SecurityProperties {

    private Boolean accessCheck = true;

    /**
     * 忽略权限校验的请求
     */
    private Map<String, String> ignoreUrls = new HashMap<>();

    /**
     * 只校验认证信息的请求
     * 登录即可访问
     */
    private Map<String, String> authenticatedUrls = new HashMap<>();

    private Boolean adminHasAllPermission = true;

    private String roleSuperAdmin = "ROLE_PLATFORM_ADMIN";

    private String defaultPasswd = "000000";
}
