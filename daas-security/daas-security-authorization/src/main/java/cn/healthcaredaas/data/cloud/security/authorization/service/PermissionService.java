package cn.healthcaredaas.data.cloud.security.authorization.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.constants.HttpHeaders;
import cn.healthcaredaas.data.cloud.core.feign.FeignSign;
import cn.healthcaredaas.data.cloud.security.authorization.cache.AuthorityCache;
import cn.healthcaredaas.data.cloud.security.core.entity.OAuth2GrantedAuthority;
import cn.healthcaredaas.data.cloud.security.core.properties.SecurityProperties;
import cn.healthcaredaas.data.cloud.security.core.resource.ResourceSecurity;
import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>权限校验服务</pre>
 *
 * @ClassName： PermissionService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/14 09:39
 * @Modify：
 */
@Service
@Slf4j
public class PermissionService {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthorityCache authorityCache;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(Authentication authentication, HttpServletRequest request) {
        if (!securityProperties.getAccessCheck()) {
            return true;
        }

        //feign内部调用
        String feignApplication = request.getHeader(HttpHeaders.X_FEIGN_REQUEST);
        String signature = request.getHeader(HttpHeaders.X_FEIGN_SIGN);
        if (!StrUtil.hasBlank(feignApplication, signature)) {
            return FeignSign.verifySign(signature, feignApplication);
        }

        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return false;
        }
        // 匿名用户
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        //系统默认的登录即可访问的请求
        for (String resource : ResourceSecurity.getAuthenticatedArray()) {
            boolean granted = antPathMatcher.match(resource, request.getRequestURI());
            if (granted) {
                return true;
            }
        }

        //配置文件中配置的登录即可访问的请求
        if (MapUtil.isNotEmpty(securityProperties.getAuthenticatedUrls())) {
            for (Map.Entry<String, String> m : securityProperties.getAuthenticatedUrls().entrySet()) {
                boolean granted = antPathMatcher.match(m.getKey(), request.getRequestURI())
                        && (m.getValue().contains(request.getMethod()) || m.getValue().contains("*"));
                if (granted) {
                    return true;
                }
            }
        }

        // 客户端登录模式
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            Boolean isClient = jwt.getClaimAsBoolean(SecurityConstants.IS_CLIENT);
            if (BooleanUtil.isTrue(isClient)) {
                // 客户端资源授权
                return false;
            }
        }

        List<OAuth2GrantedAuthority> authorities = (List<OAuth2GrantedAuthority>) authentication.getAuthorities();
        // 超级管理员
        if (securityProperties.getAdminHasAllPermission() &&
                hasRole(authorities, securityProperties.getRoleSuperAdmin())) {
            return true;
        }
        Map<String, String> urlAuthorities = new HashMap<>(16);
        //获取角色请求权限缓存，动态判断权限
        for (OAuth2GrantedAuthority authority : authorities) {
            Map<String, String> roleAuthorities = authorityCache.getAuthorities(authority.getAuthority());
            if (MapUtil.isNotEmpty(roleAuthorities)) {
                urlAuthorities.putAll(roleAuthorities);
            }
        }
        for (Map.Entry<String, String> entry : urlAuthorities.entrySet()) {
            if (StrUtil.isNotEmpty(entry.getKey())
                    && antPathMatcher.match(entry.getKey(), request.getRequestURI())
                    && request.getMethod().equalsIgnoreCase(entry.getValue())) {
                return true;
            }
        }

        return false;
    }

    private boolean hasRole(List<OAuth2GrantedAuthority> authorities, String role) {
        for (GrantedAuthority auth : authorities) {
            if (role.equalsIgnoreCase(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}