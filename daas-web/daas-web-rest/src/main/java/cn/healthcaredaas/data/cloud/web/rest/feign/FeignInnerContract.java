package cn.healthcaredaas.data.cloud.web.rest.feign;

import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.constants.HttpHeaders;
import cn.healthcaredaas.data.cloud.core.feign.FeignSign;
import cn.healthcaredaas.data.cloud.core.feign.Inner;
import feign.MethodMetadata;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**

 * @ClassName： FeignInnerContract.java
 * @Author： chenpan
 * @Date：2024/11/30 11:42
 * @Modify：
 */
public class FeignInnerContract extends SpringMvcContract {
    private final String application;

    public FeignInnerContract(String application) {
        this.application = application;
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {

        if (Inner.class.isInstance(methodAnnotation)) {
            Inner inner = findMergedAnnotation(method, Inner.class);
            if (ObjectUtils.isNotEmpty(inner)) {
                if (StrUtil.isNotBlank(application)) {
                    data.template().header(HttpHeaders.X_FEIGN_SIGN, FeignSign.signData(application));
                }
                data.template().header(HttpHeaders.X_FEIGN_REQUEST, application);
            }
        }

        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
