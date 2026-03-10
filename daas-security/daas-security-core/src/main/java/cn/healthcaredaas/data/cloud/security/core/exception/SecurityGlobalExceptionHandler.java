package cn.healthcaredaas.data.cloud.security.core.exception;

import cn.healthcaredaas.data.cloud.core.enums.ResultErrorCodes;
import cn.healthcaredaas.data.cloud.core.exception.handler.GlobalExceptionHandler;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.security.core.constants.OAuth2ErrorKeys;
import cn.healthcaredaas.data.cloud.web.core.hanlder.GlobalExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>统一异常处理器</pre>
 *
 * @ClassName： SecurityGlobalExceptionHandler.java
 * @Author： chenpan
 * @Date：2024/12/1 11:32
 * @Modify：
 */
@Slf4j
@RestControllerAdvice
public class SecurityGlobalExceptionHandler extends GlobalExceptionAdvice {

    private static final Map<String, RestResult<String>> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCESS_DENIED, GlobalExceptionHandler.getForbiddenResult(ResultErrorCodes.ACCESS_DENIED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INSUFFICIENT_SCOPE, GlobalExceptionHandler.getForbiddenResult(ResultErrorCodes.INSUFFICIENT_SCOPE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_CLIENT, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.INVALID_CLIENT));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_GRANT, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.INVALID_GRANT));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REDIRECT_URI, GlobalExceptionHandler.getPreconditionFailedResult(ResultErrorCodes.INVALID_REDIRECT_URI));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_REQUEST, GlobalExceptionHandler.getPreconditionFailedResult(ResultErrorCodes.INVALID_REQUEST));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_SCOPE, GlobalExceptionHandler.getPreconditionFailedResult(ResultErrorCodes.INVALID_SCOPE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.INVALID_TOKEN, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.INVALID_TOKEN));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SERVER_ERROR, GlobalExceptionHandler.getInternalServerErrorResult(ResultErrorCodes.SERVER_ERROR));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.TEMPORARILY_UNAVAILABLE, GlobalExceptionHandler.getServiceUnavailableResult(ResultErrorCodes.TEMPORARILY_UNAVAILABLE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNAUTHORIZED_CLIENT, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.UNAUTHORIZED_CLIENT));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_GRANT_TYPE, GlobalExceptionHandler.getNotAcceptableResult(ResultErrorCodes.UNSUPPORTED_GRANT_TYPE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_RESPONSE_TYPE, GlobalExceptionHandler.getNotAcceptableResult(ResultErrorCodes.UNSUPPORTED_RESPONSE_TYPE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.UNSUPPORTED_TOKEN_TYPE, GlobalExceptionHandler.getNotAcceptableResult(ResultErrorCodes.UNSUPPORTED_TOKEN_TYPE));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_EXPIRED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.ACCOUNT_EXPIRED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.BAD_CREDENTIALS, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.BAD_CREDENTIALS));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.CREDENTIALS_EXPIRED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.CREDENTIALS_EXPIRED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_DISABLED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.ACCOUNT_DISABLED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_LOCKED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.ACCOUNT_LOCKED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.ACCOUNT_ENDPOINT_LIMITED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.ACCOUNT_ENDPOINT_LIMITED));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.USERNAME_NOT_FOUND, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.USERNAME_NOT_FOUND));
        EXCEPTION_DICTIONARY.put(OAuth2ErrorKeys.SESSION_EXPIRED, GlobalExceptionHandler.getUnauthorizedResult(ResultErrorCodes.SESSION_EXPIRED));
    }

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return Result 对象
     */
    @ExceptionHandler({AuthenticationException.class, AuthenticationServiceException.class})
    public static RestResult<String> authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler({OAuth2AuthenticationException.class})
    public static RestResult<String> oAuth2AuthenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ExceptionHandler(PlatformAuthenticationException.class)
    public static RestResult<String> platformAuthenticationException(PlatformAuthenticationException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveSecurityException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 静态解析认证异常
     *
     * @param exception 错误信息
     * @return Result 对象
     */
    public static RestResult<String> resolveSecurityException(Exception exception, String path) {

        Exception reason = new Exception();
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException oAuth2AuthenticationException = (OAuth2AuthenticationException) exception;
            OAuth2Error oAuth2Error = oAuth2AuthenticationException.getError();
            if (EXCEPTION_DICTIONARY.containsKey(oAuth2Error.getErrorCode())) {
                RestResult<String> result = EXCEPTION_DICTIONARY.get(oAuth2Error.getErrorCode());
                result.path(path);
                result.stackTrace(exception.getStackTrace());
                result.detail(exception.getMessage());
                return result;
            }
        } else if (exception instanceof InsufficientAuthenticationException) {
            Throwable throwable = exception.getCause();
            if (ObjectUtils.isNotEmpty(throwable)) {
                reason = new Exception(throwable);
            } else {
                reason = exception;
            }
            log.debug("[DaaS] InsufficientAuthenticationException cause content is [{}]", reason.getClass().getSimpleName());
        } else {
            reason = exception;
        }

        return GlobalExceptionHandler.resolveException(reason, path);
    }
}
