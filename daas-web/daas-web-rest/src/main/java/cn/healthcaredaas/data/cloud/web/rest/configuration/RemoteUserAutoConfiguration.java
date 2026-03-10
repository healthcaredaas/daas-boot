package cn.healthcaredaas.data.cloud.web.rest.configuration;

import cn.healthcaredaas.data.cloud.web.rest.filter.UserInfoFilter;
import cn.healthcaredaas.data.cloud.web.rest.remote.RemoteUserService;
import cn.healthcaredaas.data.cloud.web.rest.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**

 * @ClassName： RemoteUserAutoConfiguration.java
 * @Author： chenpan
 * @Date：2024/12/16 16:23
 * @Modify：
 */
@Slf4j
public class RemoteUserAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public UserInfoService userInfoService(RemoteUserService remoteUserService) {
        return new UserInfoService(remoteUserService);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserInfoFilter userInfoFilter(UserInfoService userInfoService) {
        return new UserInfoFilter(userInfoService);
    }
}
