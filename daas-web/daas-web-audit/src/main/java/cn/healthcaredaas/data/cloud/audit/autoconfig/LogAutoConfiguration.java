package cn.healthcaredaas.data.cloud.audit.autoconfig;

import cn.healthcaredaas.data.cloud.audit.aspect.OperationLogAspect;
import cn.healthcaredaas.data.cloud.audit.filter.CachingRequestFilter;
import cn.healthcaredaas.data.cloud.audit.listener.OperationLogEventListener;
import cn.healthcaredaas.data.cloud.audit.remote.ILogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description: 日志自动配置
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/8 12:50
 * @Modify：
 */
@EnableAsync
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
@EnableFeignClients
public class LogAutoConfiguration {

    @Autowired
    private ILogClient logClient;

    @Bean
    public OperationLogEventListener operationLogEventListener() {
        return new OperationLogEventListener(logClient);
    }

    @Bean
    public OperationLogAspect operationLogAspect() {
        return new OperationLogAspect();
    }

    @Bean
    public CachingRequestFilter cachingRequestFilter() {
        return new CachingRequestFilter();
    }
}
