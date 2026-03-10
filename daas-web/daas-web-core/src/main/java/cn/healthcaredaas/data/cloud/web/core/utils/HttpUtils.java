package cn.healthcaredaas.data.cloud.web.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.constants.HttpHeaders;
import cn.healthcaredaas.data.cloud.core.jackson.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * <pre>Http与Servlet工具类</pre>
 *
 * @ClassName： HttpUtils.java
 * @Author： chenpan
 * @Date：2024/11/30 11:05
 * @Modify：
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return {HttpServletResponse}
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response HttpServletResponse
     * @param object   需要转换的对象
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        renderJson(response, JacksonUtils.toJson(object), MediaType.APPLICATION_JSON.toString());
    }

    /**
     * 客户端返回字符串
     *
     * @param response HttpServletResponse
     * @param string   需要绘制的信息
     */
    public static void renderJson(HttpServletResponse response, String string, String type) {
        try {
            response.setContentType(type);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(string);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("[DaaS] Render response to Json error!");
        }
    }

    /**
     * 获取ip
     *
     * @return {String}
     */
    public static String getIP() {
        return getIP(HttpUtils.getRequest());
    }

    /**
     * 获取agent
     *
     * @return {String}
     */
    public static String getAgent() {
        return getAgent(HttpUtils.getRequest());
    }

    /**
     * 获取agent
     *
     * @param request HttpServletRequest
     * @return {String}
     */
    public static String getAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    /**
     * 获取ip
     *
     * @param request HttpServletRequest
     * @return {String}
     */
    public static String getIP(HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.WL_PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.HTTP_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.HTTP_X_FORWARDED_FOR);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HttpHeaders.X_REAL_IP);
        }
        if (StrUtil.isBlank(ip) || HttpHeaders.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StrUtil.isBlank(ip) ? null : ip.split(StrUtil.COMMA)[0];
    }

    /**
     * 获取uri
     */
    public static String getUri() {
        return HttpUtils.getRequest().getRequestURI();
    }
}
