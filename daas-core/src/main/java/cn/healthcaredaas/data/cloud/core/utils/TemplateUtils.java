package cn.healthcaredaas.data.cloud.core.utils;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.util.Map;

/**

 * @ClassName： TemplateUtils.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/2/23 16:49
 * @Modify：
 */
public class TemplateUtils {

    private final static TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

    public static String render(String templateStr, Map<String, Object> params) {
        Template template = engine.getTemplate(templateStr);
        return template.render(params);
    }
}
