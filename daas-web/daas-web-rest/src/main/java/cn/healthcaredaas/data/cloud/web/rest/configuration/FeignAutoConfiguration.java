package cn.healthcaredaas.data.cloud.web.rest.configuration;

import cn.healthcaredaas.data.cloud.web.rest.feign.FeignErrorDecoder;
import cn.healthcaredaas.data.cloud.web.rest.feign.FeignInnerContract;
import cn.healthcaredaas.data.cloud.web.rest.feign.FeignRequestInterceptor;
import feign.*;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**

 * @ClassName： FeignAutoConfiguration.java
 * @Author： chenpan
 * @Date：2024/11/30 11:40
 * @Modify：
 */
@Configuration
@Slf4j
public class FeignAutoConfiguration {

    @Value("${spring.application.name:''}")
    private String application;

    @Bean
    @ConditionalOnMissingBean
    public Contract contract() {
        return new FeignInnerContract(application);
    }

    @PreDestroy
    public void destroy() {
    }

    @Bean
    @ConditionalOnMissingBean(FeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        FeignRequestInterceptor feignRequestInterceptor = new FeignRequestInterceptor();
        log.trace("[DaaS] Bean [Feign Request Interceptor] Auto Configure.");
        return feignRequestInterceptor;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * Feign Logger 配置<br/>
     * 1. NONE（默认） --- 不记录任何日志<br/>
     * 2. BASIC ---	仅记录请求方法，URL，响应状态代码以及执行时间（适合生产环境）<br/>
     * 3. HEADERS --- 记录BASIC级别的基础上，记录请求和响应的header<br/>
     * 4. FULL --- 记录请求和响应header，body和元数据<br/>
     *
     * @return feign 日志级别
     */
    @Bean
    public feign.Logger.Level logger() {
        return Logger.Level.FULL;
    }

    /**
     * FeignClient超时设置
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
