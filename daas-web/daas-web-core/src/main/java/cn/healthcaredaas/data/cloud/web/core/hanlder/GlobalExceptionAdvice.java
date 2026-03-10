package cn.healthcaredaas.data.cloud.web.core.hanlder;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.core.exception.handler.GlobalExceptionHandler;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>统一全局异常处理</pre>
 *
 * @ClassName： GlobalExceptionAdvice.java
 * @Author： chenpan
 * @Date：2024/12/9 15:50
 * @Modify：
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * Rest Template 错误处理
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return Result 对象
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public RestResult<String> restTemplateException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 参数校验异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RestResult<String> validationMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response) {
        return validationBindException(ex, request, response);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public RestResult<String> validationBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());

        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        //返回第一个错误的信息
        if (ObjectUtils.isNotEmpty(fieldError)) {
            result.validation(fieldError.getDefaultMessage(), fieldError.getCode(), fieldError.getField());
            result.setMessage(String.format("[%s]%s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 参数缺失异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public RestResult<String> missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 参数不匹配异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public RestResult<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 平台自定义异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PlatformException.class)
    public RestResult<String> platformException(PlatformException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({NoHandlerFoundException.class})
    public RestResult<String> notFoundException(NoHandlerFoundException ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class, HttpMessageNotReadableException.class})
    public RestResult<String> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        RestResult<String> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }


    public RestResult<String> resolveException(Exception ex, String path) {
        return GlobalExceptionHandler.resolveException(ex, path);
    }
}
