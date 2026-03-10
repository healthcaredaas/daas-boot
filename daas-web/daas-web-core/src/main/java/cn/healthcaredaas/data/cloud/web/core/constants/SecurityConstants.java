package cn.healthcaredaas.data.cloud.web.core.constants;

/**

 * @ClassName： SecurityConstants.java
 * @Author： chenpan
 * @Date：2024/11/30 16:47
 * @Modify：
 */
public interface SecurityConstants {
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer";
    String BASIC_TYPE = "Basic";
    /**
     * 用户名信息头
     */
    String USER_HEADER = "X-DaaS-User";

    /**
     * 用户ID信息头
     */
    String USER_ID_HEADER = "X-DaaS-Userid";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "X-DaaS-Role";

    /**
     * 客户端信息头
     */
    String CLIENT_HEADER = "X-DaaS-Client";

    String INNER_FEIGN_HEADER = "X-DaaS-Inner";

    String OPEN_ID = "openid";

    String USER_ID = "user_id";

    String ROLES = "roles";

    String ROLE_PREFIX = "ROLE_";
    String AUTHORITIES = "authorities";

    String IS_CLIENT = "client";

    String DEFAULT_AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    String DEFAULT_TOKEN_ENDPOINT = "/oauth2/token";
}
