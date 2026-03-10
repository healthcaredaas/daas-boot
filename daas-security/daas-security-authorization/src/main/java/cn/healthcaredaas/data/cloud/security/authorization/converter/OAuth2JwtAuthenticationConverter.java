package cn.healthcaredaas.data.cloud.security.authorization.converter;

import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**

 * @ClassName： JwtAuthenticationConverter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/10 15:39
 * @Modify：
 */
public class OAuth2JwtAuthenticationConverter extends JwtAuthenticationConverter {

    public OAuth2JwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.AUTHORITIES);
        this.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }
}
