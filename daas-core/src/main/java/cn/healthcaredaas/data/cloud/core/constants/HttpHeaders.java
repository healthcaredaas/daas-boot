package cn.healthcaredaas.data.cloud.core.constants;

/**

 * @ClassName： HttpHeaders.java
 * @Author： chenpan
 * @Date：2024/11/28 17:12
 * @Modify：
 */
public interface HttpHeaders {
    String X_TENANT_ID = "X-TENANT-ID";
    String X_FEIGN_REQUEST ="X-FEIGN-FROM-IN";
    String X_FEIGN_SIGN ="X-FEIGN-SIGN";
    String X_FORWARDED_FOR = "X-Forwarded-For";
    String UNKNOWN = "unknown";
    String PROXY_CLIENT_IP = "Proxy-Client-IP";
    String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    String X_REAL_IP = "X-Real-IP";
    String X_REQUEST_ID = "X-Request-ID";
    String USER_AGENT = "User-Agent";
    String CONTENT_LENGTH = "Content-Length";

}
