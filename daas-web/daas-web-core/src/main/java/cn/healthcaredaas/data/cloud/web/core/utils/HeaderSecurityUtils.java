package cn.healthcaredaas.data.cloud.web.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenpan
 * @date 2020/6/3 09:45
 */
public class HeaderSecurityUtils {

    public static String getUserIdFromToken() {
        String token = getToken();
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        JWT jwt = JWTUtil.parseToken(token);
        return jwt.getPayloads().getStr(SecurityConstants.USER_ID);
    }

    public static String getToken() {
        String token = getHeader(SecurityConstants.AUTHORIZATION);
        if (token != null && token.contains(SecurityConstants.BEARER)) {
            return token.substring(SecurityConstants.BEARER.length() + 1);
        }
        return null;
    }

    private static String getHeader(String headerName) {
        HttpServletRequest request = HttpUtils.getRequest();
        return request.getHeader(headerName);

    }

    public static String getUserName() {
        return getHeader(SecurityConstants.USER_HEADER);
    }

    public static String getUserId() {
        return getHeader(SecurityConstants.USER_ID_HEADER);
    }

    public static boolean hasRole(String role) {
        Set<String> auths = getUserAuth();
        return CollectionUtil.contains(auths, role);
    }

    public static String getClient() {
        return getHeader(SecurityConstants.CLIENT_HEADER);
    }

    public static Set<String> getUserAuth() {
        String auth = getHeader(SecurityConstants.ROLE_HEADER);
        if (auth != null) {
            return new HashSet<>(Arrays.asList(auth.split(",")));
        }
        return null;
    }
}
