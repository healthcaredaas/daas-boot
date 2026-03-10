package cn.healthcaredaas.data.cloud.core.context;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**

 * @ClassName： ThreadLocalContextUtils.java
 * @Author： chenpan
 * @Date：2024/11/28 16:00
 * @Modify：
 */
public class ThreadLocalContextUtils {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static String getString(String attribute) {
        Object object = get(attribute);
        if (ObjectUtils.isNotEmpty(object) && object instanceof String) {
            return (String) object;
        }

        return null;
    }

    /**
     * 获得线程中保存的属性.
     *
     * @param attribute 属性名称
     * @return 属性值
     */
    public static Object get(String attribute) {
        Map<String, Object> map = threadLocal.get();
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        return map.get(attribute);
    }

    public static void set(String attribute, Object value) {
        Map<String, Object> map = threadLocal.get();

        if (MapUtils.isEmpty(map)) {
            map = new ConcurrentHashMap<>(8);
        }
        map.put(attribute, value);
        threadLocal.set(map);
    }

    /**
     * 清除线程中保存的数据
     */
    public static void clear() {
        threadLocal.remove();
    }
}
