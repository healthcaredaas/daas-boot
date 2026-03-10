package cn.healthcaredaas.data.cloud.core.exception.handler;

import cn.healthcaredaas.data.cloud.core.enums.ResultErrorCodes;
import cn.healthcaredaas.data.cloud.core.exception.DaaSException;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**

 * @ClassName： GlobalExceptionHandler.java
 * @Author： chenpan
 * @Date：2024/11/28 15:42
 * @Modify：
 */
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Map<String, RestResult<String>> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        // 401.** 对应错误
        EXCEPTION_DICTIONARY.put("InsufficientAuthenticationException", getUnauthorizedResult(ResultErrorCodes.INSUFFICIENT_AUTHORIZED));
        // 403
        EXCEPTION_DICTIONARY.put("AccessDeniedException", getForbiddenResult(ResultErrorCodes.ACCESS_DENIED));
        // 405.** 对应错误
        EXCEPTION_DICTIONARY.put("HttpRequestMethodNotSupportedException", getResult(ResultErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED, HttpStatus.SC_METHOD_NOT_ALLOWED));
        // 415.** 对应错误
        EXCEPTION_DICTIONARY.put("HttpMediaTypeNotAcceptableException", getUnsupportedMediaTypeResult(ResultErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE));
        // 5*.** 对应错误
        EXCEPTION_DICTIONARY.put("IllegalArgumentException", getInternalServerErrorResult(ResultErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION));
        EXCEPTION_DICTIONARY.put("MethodArgumentTypeMismatchException", getInternalServerErrorResult(ResultErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION));
        EXCEPTION_DICTIONARY.put("NullPointerException", getInternalServerErrorResult(ResultErrorCodes.NULL_POINTER_EXCEPTION));
        EXCEPTION_DICTIONARY.put("IOException", getInternalServerErrorResult(ResultErrorCodes.IO_EXCEPTION));
        EXCEPTION_DICTIONARY.put("HttpMessageNotReadableException", getInternalServerErrorResult(ResultErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION));
        EXCEPTION_DICTIONARY.put("TypeMismatchException", getInternalServerErrorResult(ResultErrorCodes.TYPE_MISMATCH_EXCEPTION));
        EXCEPTION_DICTIONARY.put("MissingServletRequestParameterException", getInternalServerErrorResult(ResultErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION));
        EXCEPTION_DICTIONARY.put("ProviderNotFoundException", getServiceUnavailableResult(ResultErrorCodes.PROVIDER_NOT_FOUND));
        EXCEPTION_DICTIONARY.put("CookieTheftException", getServiceUnavailableResult(ResultErrorCodes.COOKIE_THEFT));
        EXCEPTION_DICTIONARY.put("InvalidCookieException", getServiceUnavailableResult(ResultErrorCodes.INVALID_COOKIE));
        // 6*.** 对应错误
        // EXCEPTION_DICTIONARY.put("BadSqlGrammarException", getInternalServerErrorResult(ResultErrorCodes.BAD_SQL_GRAMMAR));
        EXCEPTION_DICTIONARY.put("DataIntegrityViolationException", getInternalServerErrorResult(ResultErrorCodes.DATA_INTEGRITY_VIOLATION));
        EXCEPTION_DICTIONARY.put("TransactionRollbackException", getInternalServerErrorResult(ResultErrorCodes.TRANSACTION_ROLLBACK));
        EXCEPTION_DICTIONARY.put("BindException", getNotAcceptableResult(ResultErrorCodes.METHOD_ARGUMENT_NOT_VALID));
        EXCEPTION_DICTIONARY.put("MethodArgumentNotValidException", getNotAcceptableResult(ResultErrorCodes.METHOD_ARGUMENT_NOT_VALID));
        // 7*.** 对应错误
        EXCEPTION_DICTIONARY.put("RedisPipelineException", getResult(ResultErrorCodes.PIPELINE_INVALID_COMMANDS, HttpStatus.SC_INTERNAL_SERVER_ERROR));
    }

    protected static RestResult<String> getResult(ResultErrorCodes resultErrorCodes, int httpStatus) {
        return RestResult.failure(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), httpStatus, null);
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param resultCode 401
     * @return {@link RestResult}
     */
    public static RestResult<String> getUnauthorizedResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNAUTHORIZED);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param resultCode 403
     * @return {@link RestResult}
     */
    public static RestResult<String> getForbiddenResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     *
     * @param resultCode 406
     * @return {@link RestResult}
     */
    public static RestResult<String> getNotAcceptableResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    /**
     * 412 Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param resultCode 412
     * @return {@link RestResult}
     */
    public static RestResult<String> getPreconditionFailedResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_PRECONDITION_FAILED);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param resultCode 415
     * @return {@link RestResult}
     */
    private static RestResult<String> getUnsupportedMediaTypeResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     *
     * @param resultCode 500
     * @return {@link RestResult}
     */
    public static RestResult<String> getInternalServerErrorResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     *
     * @param resultCode 503
     * @return {@link RestResult}
     */
    public static RestResult<String> getServiceUnavailableResult(ResultErrorCodes resultCode) {
        return getResult(resultCode, HttpStatus.SC_SERVICE_UNAVAILABLE);
    }

    public static RestResult<String> resolveException(Exception ex, String path) {
        ex.printStackTrace();
        log.trace("[DaaS] Global Exception Handler, Path : [{}], Exception : [{}]", path, ex);
        if (ex instanceof DaaSException) {
            DaaSException exception = (DaaSException) ex;
            RestResult<String> result = exception.getResult();
            result.path(path);
            log.error("[DaaS] Global Exception: {}", result);
            return result;
        } else {
            RestResult<String> result = RestResult.failure();
            String exceptionName = ex.getClass().getSimpleName();
            if (StringUtils.isNotEmpty(exceptionName) && EXCEPTION_DICTIONARY.containsKey(exceptionName)) {
                result = EXCEPTION_DICTIONARY.get(exceptionName);
            }

            result.path(path);
            result.stackTrace(ex.getStackTrace());
            result.detail(ex.getMessage());
            log.error("[DaaS] Global Exception: {}", result);
            return result;
        }
    }
}
