package cn.healthcaredaas.data.cloud.ops.metric.collector;

import cn.healthcaredaas.data.cloud.core.utils.OkHttpUtils;
import cn.healthcaredaas.data.cloud.ops.metric.config.MetricCollectorProperties;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Description: 指标推送
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:39
 * @Modify：
 */
@Slf4j
@Component
public class MetricPusher {

    @Autowired
    private MetricCollectorProperties properties;

    /**
     * 批量上报数据
     *
     * @param baseMetrics 指标数据集合
     */
    public <T extends BaseMetric> void push(Collection<T> baseMetrics) {
        if (CollectionUtil.isNotEmpty(baseMetrics)) {
            try {
                String result = OkHttpUtils.doPost(properties.getUrl(), JSONObject.from(baseMetrics));
                log.debug("上报数据：{},上报结果：{}", JSON.toJSONString(baseMetrics), result);
            } catch (Exception e) {
                log.error("metrics push异常,检查监控组件是否运行正常", e);
            }
        } else {
            log.warn("推送指标数据为空！");
        }
    }

    /**
     * 上报数据
     *
     * @param baseMetric 指标数据
     */
    public <T extends BaseMetric> void push(T baseMetric) {
        if (baseMetric != null) {
            try {
                String result = OkHttpUtils.doPost(properties.getUrl(), JSONObject.from(baseMetric));
                log.debug("上报数据：{}", result);
            } catch (Exception e) {
                log.error("metrics push异常,检查监控组件是否运行正常", e);
            }
        } else {
            log.warn("推送指标数据为空！");
        }
    }
}
