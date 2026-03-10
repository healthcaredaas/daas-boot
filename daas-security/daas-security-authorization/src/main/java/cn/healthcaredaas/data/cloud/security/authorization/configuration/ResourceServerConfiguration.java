package cn.healthcaredaas.data.cloud.security.authorization.configuration;

import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.security.authorization.access.CustomAuthorizationManager;
import cn.healthcaredaas.data.cloud.security.authorization.converter.OAuth2JwtAuthenticationConverter;
import cn.healthcaredaas.data.cloud.security.core.properties.SecurityProperties;
import cn.healthcaredaas.data.cloud.security.core.handler.OAuth2AccessDeniedHandler;
import cn.healthcaredaas.data.cloud.security.core.handler.OAuth2AuthenticationEntryPoint;
import cn.healthcaredaas.data.cloud.security.core.resource.ResourceSecurity;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.Resource;
import java.util.Arrays;

/**
 * <pre>Web安全配置</pre>
 *
 * @ClassName： ResourceServerConfiguration.java
 * @Author： chenpan
 * @Date：2024/12/7 13:38
 * @Modify：
 */
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class ResourceServerConfiguration {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private CustomAuthorizationManager authorizationManager;

    /**
     * spring security 默认的安全策略
     *
     * @param http security注入点
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 禁用CSRF 开启跨域
        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // @formatter:off
        http.authorizeHttpRequests(authorize -> {
            //自定义忽略校验请求
            securityProperties.getIgnoreUrls().forEach((url, methods) -> {
                if (StrUtil.isNotEmpty(methods)) {
                    Arrays.asList(methods.split(",")).forEach(method -> {
                        if (StrUtil.isNotBlank(method)) {
                            authorize.requestMatchers(HttpMethod.valueOf(method.toUpperCase()), url).permitAll();
                        }
                    });
                }
            });
            authorize
                    .requestMatchers(ResourceSecurity.getPermitAllArray()).permitAll()
                    .requestMatchers(ResourceSecurity.getStaticResourceArray()).permitAll()
                    .requestMatchers(ResourceSecurity.getAuthenticatedArray()).authenticated()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                    .anyRequest().access(authorizationManager);
        });
        if (securityProperties.getAccessCheck()) {
            http.oauth2ResourceServer(oauth2 -> {
                oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(new OAuth2JwtAuthenticationConverter()))
                        .bearerTokenResolver(new DefaultBearerTokenResolver())
                        .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
                        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
            });
        }
        http.exceptionHandling()
                .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
        // @formatter:on

        return http.build();
    }

}
