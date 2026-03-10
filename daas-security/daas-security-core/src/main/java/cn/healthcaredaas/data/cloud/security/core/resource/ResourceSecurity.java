package cn.healthcaredaas.data.cloud.security.core.resource;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.healthcaredaas.data.cloud.security.core.constants.OAuthEndpoint;

import java.util.List;

/**

 * @ClassName： ResourceSecurity.java
 * @Author： chenpan
 * @Date：2024/12/1 13:54
 * @Modify：
 */
public class ResourceSecurity {

    private static final List<String> DEFAULT_IGNORED_STATIC_RESOURCES = ListUtil.toList(
            "/error/**",
            "/plugins/**",
            "/authorization/**",
            "/static/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/openapi.json",
            "/favicon.ico");

    private static final List<String> DEFAULT_PERMIT_ALL_RESOURCES = ListUtil.toList(
            "/open/**",
            "/oauth2/check_token",
            "/oauth2/sign-out");

    private static final List<String> DEFAULT_AUTHENTICATED_RESOURCES = ListUtil.toList(
            OAuthEndpoint.USER_PROFILE,
            OAuthEndpoint.CHANGE_PASSWORD,
            OAuthEndpoint.USER_MENU,
            OAuthEndpoint.USER_APP
    );


    public static String[] getPermitAllArray() {
        return ArrayUtil.toArray(DEFAULT_PERMIT_ALL_RESOURCES, String.class);
    }

    public static String[] getStaticResourceArray() {
        return ArrayUtil.toArray(DEFAULT_IGNORED_STATIC_RESOURCES, String.class);
    }

    public static String[] getAuthenticatedArray() {
        return ArrayUtil.toArray(DEFAULT_AUTHENTICATED_RESOURCES, String.class);
    }

}
