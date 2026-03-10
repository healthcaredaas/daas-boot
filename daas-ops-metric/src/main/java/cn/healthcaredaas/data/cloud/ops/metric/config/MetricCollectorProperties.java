package cn.healthcaredaas.data.cloud.ops.metric.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 指标收集配置
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:35
 * @Modify：
 */
@Configuration
@ConfigurationProperties(prefix = "metric.collector")
@Data
public class MetricCollectorProperties {

    private boolean enable = false;

    private String url;
}
