package cn.healthcaredaas.data.cloud.ops.metric.collector;

import lombok.Builder;
import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 指标
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:38
 * @Modify：
 */
@Data
@Builder
public class MetricCollector {

    /**
     * 监控指标名称
     */
    private String metric;

    /**
     * 监控实体
     */
    private String endpoint;

    /**
     * 监控数据的属性标签
     */
    private Map<String, String> tags;

    /**
     * 监控指标的当前值
     */
    private Double value;

    /**
     * 当前时间戳，单位是秒
     */
    private long timestamp;

    /**
     * 附加信息
     */
    private String extra;

    public void ident(String ident) {
        if (this.getTags() == null) {
            tags = new HashMap<>(16);
        }
        tags.put("ident", ident);
    }

    public void addTag(String tagK, String tagV) {
        if (this.getTags() == null) {
            tags = new HashMap<>(16);
        }
        tags.put(tagK, tagV);
    }

    public void endpoint() throws UnknownHostException {
        setEndpoint(InetAddress.getLocalHost().getHostAddress());
    }
}
