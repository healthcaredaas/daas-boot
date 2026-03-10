package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.healthcaredaas.data.cloud.core.utils.TemplateUtils;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.ITransformFunction;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import cn.healthcaredaas.data.cloud.data.dataformat.vo.FunctionVo;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenpan
 * @date 2020/1/6 12:51
 */
@Component
public class TransformFunctionService {
    private static final String TRANSFORM_FUNC_PKG = "cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func";
    private static final List<FunctionVo> FUNCTIONS = new ArrayList<>();

    static {
        Set<Class<?>> classSet = new Reflections(TRANSFORM_FUNC_PKG).getTypesAnnotatedWith(TransFunction.class);
        if (CollectionUtil.isNotEmpty(classSet)) {
            for (Class<?> cls : classSet) {
                TransFunction function = cls.getAnnotation(TransFunction.class);
                FUNCTIONS.add(new FunctionVo(function.code(), function.name(), function.description()));
            }
        }
    }

    public List<FunctionVo> getFunctionList() {
        return FUNCTIONS;
    }

    public String invokeFunction(String functionName, Map<String, Object> data, String params) {
        ITransformFunction function = SpringUtil.getBean(functionName);

        if (function.withoutData()) {
            return function.transform(params);
        }
        if (function.isRenderParams()) {
            return function.transform(TemplateUtils.render(params, data));
        }
        return function.transform(data.get(params));
    }
}
