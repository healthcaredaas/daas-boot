package cn.healthcaredaas.data.cloud.audit.util;

import cn.healthcaredaas.data.cloud.core.jackson.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Description: HTTP 请求体工具
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/23 19:38
 * @Modify：
 */
@Slf4j
public class HttpLogUtils {

    /**
     * 获取请求参数
     *
     * @param request HttpServletRequest
     * @return {MultiValueMap<String, String>}
     */
    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        // 处理 URI 查询参数（适用于所有 HTTP 方法）
        request.getParameterMap().forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });

        // 处理 POST、PUT、DELETE 等请求中的 body 参数
        String method = request.getMethod();
        if (method.equals(HttpMethod.POST.name()) ||
                method.equals(HttpMethod.PUT.name()) ||
                method.equals(HttpMethod.DELETE.name())) {

            try {
                // 检查是否是 ContentCachingRequestWrapper
                if (request instanceof ContentCachingRequestWrapper wrapper) {
                    byte[] content = wrapper.getContentAsByteArray();
                    if (content.length > 0) {
                        String body = new String(content, StandardCharsets.UTF_8);
                        parseRequestBody(body, request.getContentType(), parameters);
                    }
                } else {
                    // 尝试读取请求体，如果失败则跳过
                    try (BufferedReader reader = request.getReader()) {
                        String body = reader.lines().reduce("", String::concat);
                        parseRequestBody(body, request.getContentType(), parameters);
                    }
                }
            } catch (IllegalStateException e) {
                // getReader() 已经被调用过，或者 getInputStream() 已经被调用过
                log.warn("无法读取请求体，可能已经被其他组件读取过：{}", e.getMessage());
            } catch (IOException e) {
                log.warn("无法读取请求体：{}", e.getMessage());
            }
        }
        return parameters;
    }

    private static void parseRequestBody(String body, String contentType, MultiValueMap<String, String> parameters) {
        if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                // 处理JSON格式的请求体
                ObjectMapper objectMapper = JacksonUtils.getObjectMapper();
                Map<String, Object> jsonMap = objectMapper.readValue(body, Map.class);
                jsonMap.forEach((key, value) -> parameters.add(key, value != null ? value.toString() : null));
            } catch (Exception e) {
                log.warn("解析JSON请求体失败：{}", e.getMessage());
            }
        }
    }
}
