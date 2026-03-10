package cn.healthcaredaas.data.cloud.ops.metric;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 自定义计数器
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 15:31
 * @Modify：
 */
@Component
public class LocalCounter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Date getTomorrowZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public void setValue(String key, Object val, boolean onlyToday) {
        redisTemplate.opsForValue().set(key, val);
        if (onlyToday) {
            redisTemplate.expireAt(key, getTomorrowZero());
        }
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void inc(String key, boolean onlyToday) {
        redisTemplate.opsForValue().increment(key);
        if (onlyToday) {
            redisTemplate.expireAt(key, getTomorrowZero());
        }
    }

    public void inc(String key, double val, boolean onlyToday) {
        redisTemplate.opsForValue().increment(key, val);
        if (onlyToday) {
            redisTemplate.expireAt(key, getTomorrowZero());
        }
    }

    public Map<String, Double> getValues(String keyPrefix) {
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            Map<String, Double> result = new HashMap<>();
            for (String key : keys) {
                Object o = redisTemplate.opsForValue().get(key);
                if (o != null) {
                    Double count = Double.parseDouble(o.toString());
                    result.put(key.replace(keyPrefix,""), count);
                }
            }
            return result;
        }
        return null;
    }
}
