package cn.healthcaredaas.data.cloud.data.dataformat.groovy

/**
 * @ClassName ： Bindings.java
 * @Description:
 * @Author ： chenpan
 * @Date ：2024/8/5 11:43
 * @Modify ：
 */
class Bindings {
    @Delegate
    private final Map map

    Bindings(Map map) {
        this.map = map
    }

    boolean containsKey(Object key) {
        return true
    }

    Object get(Object key) {
        return map.get(key) == null ? '' : map.get(key)
    }
}