package cn.healthcaredaas.data.cloud.web.rest.configuration;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.healthcaredaas.data.cloud.web.rest.interceptor.OkHttpResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**

 * @ClassName： OkHttpAutoConfiguration.java
 * @Author： chenpan
 * @Date：2024/11/30 13:27
 * @Modify：
 */
@Configuration
@AutoConfigureBefore(FeignLoadBalancerAutoConfiguration.class)
@ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
@Slf4j
public class OkHttpAutoConfiguration {

    private OkHttpClient okHttpClient;

    @PostConstruct
    public void postConstruct() {
        log.debug("[DaaS] SDK [Engine Web OkHttp] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean(ConnectionPool.class)
    public ConnectionPool connectionPool(FeignHttpClientProperties feignHttpClientProperties) {
        int maxTotalConnections = feignHttpClientProperties.getMaxConnections();
        long timeToLive = feignHttpClientProperties.getTimeToLive();
        TimeUnit ttlUnit = feignHttpClientProperties.getTimeToLiveUnit();
        return new ConnectionPool(maxTotalConnections, timeToLive, ttlUnit);
    }

    @Bean
    public OkHttpClient okHttpClient(ConnectionPool connectionPool,
                                             FeignClientProperties feignClientProperties,
                                             FeignHttpClientProperties feignHttpClientProperties) {
        int readTimeout = 3000;
        if (CollectionUtil.isNotEmpty(feignClientProperties.getConfig())
                && ObjectUtil.isNotNull(feignClientProperties.getConfig().get("default"))
        ) {
            FeignClientProperties.FeignClientConfiguration defaultConfig = feignClientProperties.getConfig().get("default");
            readTimeout = defaultConfig.getReadTimeout();
        }
        int connectTimeout = feignHttpClientProperties.getConnectionTimeout();
        boolean disableSslValidation = feignHttpClientProperties.isDisableSslValidation();
        boolean followRedirects = feignHttpClientProperties.isFollowRedirects();
        this.okHttpClient = new OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .followRedirects(followRedirects)
                .connectionPool(connectionPool)
                .addInterceptor(new OkHttpResponseInterceptor())
                .build();
        return this.okHttpClient;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(okhttp3.OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        log.trace("[DaaS] Bean [Client Http Request Factory for OkHttp] Auto Configure.");
        return factory;
    }

    @PreDestroy
    public void destroy() {
        if (this.okHttpClient != null) {
            this.okHttpClient.dispatcher().executorService().shutdown();
            this.okHttpClient.connectionPool().evictAll();
        }
    }
}
