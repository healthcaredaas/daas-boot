package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser;

import cn.hutool.core.util.ObjectUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.GroovyScript;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import cn.healthcaredaas.data.cloud.data.dataformat.groovy.GroovyScriptFactory;
import groovy.lang.Binding;
import lombok.extern.slf4j.Slf4j;

/**

 * @ClassName： GroovySerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/5 18:00
 * @Modify：
 */
@Slf4j
public class GroovySerializer extends AdapterSerializer {

    @Override
    public String serialize() {
        MessageSchemaDefinition definition = this.getTask().getDefinition();
        if (definition == null) {
            log.warn("缺少数据转换定义信息");
            return null;
        }
        GroovyScript script = definition.getScript();
        if (ObjectUtil.isEmpty(script)) {
            log.warn("缺少脚本信息，任务：{}", getTask().getName());
            return null;
        }
        Binding binding = new Binding();
        binding.setVariable("variables", this.getData());
        binding.setVariable("template", script.getTemplate());

        Object result = GroovyScriptFactory.getInstance().scriptGetAndRun(script.getScript(), binding);
        if (result instanceof String) {
            return String.valueOf(result);
        }
        return null;
    }
}
