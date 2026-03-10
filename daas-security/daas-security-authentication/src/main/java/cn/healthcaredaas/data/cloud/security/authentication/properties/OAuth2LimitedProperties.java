package cn.healthcaredaas.data.cloud.security.authentication.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * <pre>身份鉴别安全限制</pre>
 *
 * @ClassName： OAuth2IdentifyProperties.java
 * @Author： chenpan
 * @Date：2024/12/7 09:58
 * @Modify：
 */
@Configuration
@ConfigurationProperties(prefix = "daas.oauth2.identify")
@Data
public class OAuth2LimitedProperties {

    private SignInEndpointLimited signInEndpointLimited = new SignInEndpointLimited();

    private SignInFailureLimited signInFailureLimited = new SignInFailureLimited();

    private SignInKickOutLimited signInKickOutLimited = new SignInKickOutLimited();

    /**
     * 登录终端限制
     */
    @Data
    public static class SignInEndpointLimited {
        /**
         * 同一终端登录限制是否开启，默认开启。
         */
        private Boolean enabled = false;

        /**
         * 同一终端，允许同时登录的最大数量
         */
        private Integer maximum = 1;
    }

    /**
     * 登录失败限制
     */
    @Data
    public static class SignInFailureLimited {
        /**
         * 是否开启登录失败检测，默认开启
         */
        private Boolean enabled = true;

        /**
         * 允许允许最大失败次数
         */
        private Integer maxTimes = 5;

        /**
         * 是否自动解锁被锁定用户，默认开启
         */
        private Boolean autoUnlock = true;

        /**
         * 记录失败次数的缓存过期时间，默认：2小时。
         */
        private Duration expire = Duration.ofHours(2);
    }

    /**
     * 踢出功能
     */
    @Data
    public static class SignInKickOutLimited {
        /**
         * 是否开启 Session 踢出功能，默认开启
         */
        private Boolean enabled = true;
    }
}
