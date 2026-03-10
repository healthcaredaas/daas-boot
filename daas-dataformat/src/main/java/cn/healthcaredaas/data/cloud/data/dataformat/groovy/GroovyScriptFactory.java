package cn.healthcaredaas.data.cloud.data.dataformat.groovy;

import cn.hutool.crypto.SecureUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Groovy脚本缓存执行
 */
public class GroovyScriptFactory {

    private static final Map<String, Class<Script>> scriptCache = new HashMap<>();
    private final GroovyClassLoader classLoader = new GroovyClassLoader();
    private static final GroovyScriptFactory factory = new GroovyScriptFactory();

    private GroovyScriptFactory() {
    }

    public static GroovyScriptFactory getInstance() {
        return factory;
    }

    /**
     * 获取脚本缓存
     *
     * @param script
     * @return
     */
    private Class<Script> getScript(String script) {
        // 压缩脚本节省空间
        String encodeStr = SecureUtil.md5(script);
        if (scriptCache.containsKey(encodeStr)) {
            return scriptCache.get(encodeStr);
        } else {
            // 脚本不存在则创建新的脚本
            Class<Script> scriptClass = classLoader.parseClass(script);
            scriptCache.put(encodeStr, scriptClass);
            return scriptClass;
        }
    }

    /**
     * 执行脚本
     *
     * @param script
     * @param binding
     * @return
     */
    private Object run(Class<Script> script, Binding binding) {
        Script scriptObj = InvokerHelper.createScript(script, binding);
        Object result = scriptObj.run();
        // 每次脚本执行完之后，一定要清理掉内存
        classLoader.clearCache();
        return result;
    }

    /**
     * 获取并执行脚本
     *
     * @param key
     * @param binding
     * @return
     */
    public Object scriptGetAndRun(String key, Binding binding) {
        return run(getScript(key), binding);
    }
}
