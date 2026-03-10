package cn.healthcaredaas.data.cloud.security.core.handler;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.security.core.exception.SecurityGlobalExceptionHandler;
import cn.healthcaredaas.data.cloud.web.core.utils.HttpUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>自定义未认证处理</pre>
 *
 * @ClassName： OAuth2AuthenticationEntryPoint.java
 * @Author： chenpan
 * @Date：2024/12/1 11:44
 * @Modify：
 */
public class OAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RestResult<String> result = SecurityGlobalExceptionHandler.resolveSecurityException(authException, request.getRequestURI());
        response.setStatus(result.getStatus());
        HttpUtils.renderJson(response, result);
    }
}
