package cn.healthcaredaas.data.cloud.security.core.handler;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.security.core.exception.SecurityGlobalExceptionHandler;
import cn.healthcaredaas.data.cloud.web.core.utils.HttpUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**

 * @ClassName： FilterExceptionHandler.java
 * @Author： chenpan
 * @Date：2024/12/9 16:00
 * @Modify：
 */
public class OAuth2FilterExceptionHandler extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            RestResult<String> restResult = SecurityGlobalExceptionHandler.resolveSecurityException(e, request.getRequestURI());
            HttpUtils.renderJson(response, restResult);
        }
    }
}
