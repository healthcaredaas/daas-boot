package cn.healthcaredaas.data.cloud.web.rest.feign;

import cn.healthcaredaas.data.cloud.core.constants.HttpHeaders;
import cn.hutool.extra.servlet.JakartaServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

/**
 * <pre>自定义FeignRequestInterceptor</pre>
 *
 * @ClassName： FeignRequestInterceptor.java
 * @Author： chenpan
 * @Date：2024/11/30 11:43
 * @Modify：
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (httpServletRequest != null) {
            Map<String, String> headers = JakartaServletUtil.getHeaderMap(httpServletRequest);

            log.debug("[DaaS] Feign Request Interceptor copy all need transfer header!");

            // 微服务之间传递的唯一标识,区分大小写所以通过httpServletRequest查询
            if (headers.containsKey(HttpHeaders.X_REQUEST_ID)) {
                String traceId = headers.get(HttpHeaders.X_REQUEST_ID);
                MDC.put("traceId", traceId);
                log.info("[DaaS] Feign Request Interceptor Trace: {}", traceId);
            }
        }

        log.trace("[DaaS] Feign Request Interceptor [{}]", requestTemplate.toString());
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("[DaaS] Feign Request Interceptor can not get Request.");
            return null;
        }
    }
}