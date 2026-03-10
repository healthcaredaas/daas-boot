package cn.healthcaredaas.data.cloud.security.authentication.customizer;

import cn.healthcaredaas.data.cloud.security.core.entity.OAuthUser;
import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <pre>TokenCustomizer 通用处理</pre>
 *
 * @ClassName： AbstractTokenCustomizer.java
 * @Author： chenpan
 * @Date：2024/12/1 17:08
 * @Modify：
 */
public abstract class AbstractTokenCustomizer {

    protected void appendAll(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {

        appendAuthorities(attributes, authentication);
        appendCommons(attributes, authentication, authorizedScopes);

    }

    protected void appendAuthorities(Map<String, Object> attributes, Authentication authentication) {

        if (CollectionUtils.isNotEmpty(authentication.getAuthorities())) {
            Set<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            attributes.put(SecurityConstants.AUTHORITIES, authorities);
        }
    }

    protected void appendCommons(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {

        if (CollectionUtils.isNotEmpty(authorizedScopes)) {
            attributes.put(OAuth2ParameterNames.SCOPE, authorizedScopes);
        }
        if (authentication instanceof OAuth2ClientAuthenticationToken) {
            attributes.put(SecurityConstants.IS_CLIENT, true);
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            OAuthUser principal = (OAuthUser) authentication.getPrincipal();
            putUserInfo(attributes, principal);
        }

        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
            Object details = authentication.getDetails();
            if (ObjectUtils.isNotEmpty(details) && details instanceof OAuthUser) {
                OAuthUser principal = (OAuthUser) details;
                putUserInfo(attributes, principal);
            }
        }
    }

    private void putUserInfo(Map<String, Object> attributes, OAuthUser principal) {
        attributes.put(SecurityConstants.OPEN_ID, principal.getUserId());
        attributes.put(SecurityConstants.USER_ID, principal.getUserId());
        attributes.put(SecurityConstants.ROLES, principal.getRoles());
    }
}
