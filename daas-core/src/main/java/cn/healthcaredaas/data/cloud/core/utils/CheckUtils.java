package cn.healthcaredaas.data.cloud.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.exception.BizException;

import java.util.Collection;
import java.util.Map;

/**

 * @ClassName： CheckUtils.java
 * @Description:
 * @Author： chenpan
 * @Date：2021/12/30 13:32
 * @Modify：
 */
public class CheckUtils {

    public static void throwIf(boolean asserted, String message) {
        if (asserted) {
            throw new BizException(message);
        }
    }

    public static void notNull(Object obj, String message) {
        throwIf(obj == null, message);
    }

    public static void notEmptyCollection(Collection<?> collection, String message) {
        throwIf(CollectionUtil.isEmpty(collection), message);
    }

    public static void notBlank(Object obj, String message) {
        throwIf(StrUtil.isBlankIfStr(obj), message);
    }

    public static void notEmpty(Object obj, String message) {
        notNull(obj, message);
        if (obj instanceof Collection) {
            notEmptyCollection((Collection<?>) obj, message);
        }
        if (obj instanceof Map) {
            throwIf(MapUtil.isEmpty((Map<?, ?>) obj), message);
        }
        if (ArrayUtil.isArray(obj)) {
            throwIf(ArrayUtil.isEmpty(obj), message);
        }
        if (obj instanceof String) {
            throwIf(StrUtil.isBlankIfStr(obj), message);
        }
    }
}
