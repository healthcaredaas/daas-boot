package cn.healthcaredaas.data.cloud.security.core.utils;

import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

/**

 * @ClassName： SecurityUtils.java
 * @Author： chenpanz
 * @Date：2024/12/1 11:52
 * @Modify：
 */
@Slf4j
public class SecurityUtils {

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static final String PREFIX_ROLE = "ROLE_";
    public static final String PREFIX_SCOPE = "SCOPE_";
    public static final String DEFAULT_ROLE = "USER";

    /**
     * 密码加密
     *
     * @param password 明文密码
     * @return 已加密密码
     */
    public static String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 密码验证
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Jwt getJwt() {
        Authentication authentication = getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            return (Jwt) principal;
        }
        return null;
    }

    public static Object getDetails() {
        return getAuthentication().getDetails();
    }

    public static String getUsername() {
        return getAuthentication().getName();
    }

    public static String getUserId() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            return null;
        }
        return jwt.getClaimAsString(SecurityConstants.USER_ID);
    }

    public static Set<String> getRoles() {
        Jwt jwt = getJwt();
        if (jwt == null) {
            return Collections.emptySet();
        }

        return new HashSet<>(jwt.getClaimAsStringList(SecurityConstants.AUTHORITIES));
    }

    public static boolean hasRole(String role) {
        for (String auth : getRoles()) {
            if (role.equalsIgnoreCase(auth)) {
                return true;
            }
        }
        return false;
    }

    public static String wellFormRolePrefix(String content) {
        return wellFormPrefix(content, PREFIX_ROLE);
    }

    public static String wellFormPrefix(String content, String prefix) {
        if (StringUtils.startsWith(content, prefix)) {
            return content;
        } else {
            return prefix + content;
        }
    }
}
