package cn.healthcaredaas.data.cloud.security.core.handler;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.security.core.exception.SecurityGlobalExceptionHandler;
import cn.healthcaredaas.data.cloud.web.core.utils.HttpUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>授权拒绝处理器</pre>
 *
 * @ClassName： OAuth2AccessDeniedHandler.java
 * @Author： chenpan
 * @Date：2024/12/1 11:40
 * @Modify：
 */
public class OAuth2AccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RestResult<String> result = SecurityGlobalExceptionHandler.resolveSecurityException(accessDeniedException, request.getRequestURI());
        response.setStatus(result.getStatus());
        HttpUtils.renderJson(response, result);
    }
}
