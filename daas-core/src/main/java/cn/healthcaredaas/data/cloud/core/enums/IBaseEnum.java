package cn.healthcaredaas.data.cloud.core.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>基础枚举接口</pre>
 *
 * @ClassName： IBaseEnum.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/22 16:03
 * @Modify：
 */
public interface IBaseEnum<K> {

    /**
     * 获取编码
     *
     * @return 编码
     */
    K getKey();

    /**
     * 获取描述
     *
     * @return 描述
     */
    String getValue();

    static <K, T extends IBaseEnum<K>> Map<K, String> getMap(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(IBaseEnum::getKey, IBaseEnum::getValue));
    }

    static <K, T extends IBaseEnum<K>> List<String> getKeys(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(IBaseEnum::getValue)
                .collect(Collectors.toList());
    }

    static <K, T extends IBaseEnum<K>> List<String> getLabels(Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(IBaseEnum::getValue)
                .collect(Collectors.toList());
    }

    static <K, T extends IBaseEnum<K>> T getEnum(Class<T> enumClass, K key) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(t -> t.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
    }

    static <K, T extends IBaseEnum<K>> T getEnumByValue(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
    }

    static <K, T extends IBaseEnum<K>> String getValueByKey(Class<T> enumClass, K key) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(t -> t.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""))
                .getValue();
    }

    static <K, T extends IBaseEnum<K>> K getKeyByValue(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(t -> t.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""))
                .getKey();
    }

    @SuppressWarnings("unchecked")
    static <K, T extends IBaseEnum<K>> T of(Class<T> enumClass, Object key){
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(t -> t.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }
}
