package cn.healthcaredaas.data.cloud.security.core.handler;

import cn.healthcaredaas.data.cloud.security.core.constants.OAuth2ErrorKeys;
import cn.healthcaredaas.data.cloud.security.core.exception.AccountEndpointLimitedException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * <pre>扩展的 DefaultAuthenticationEventPublisher</pre>
 *
 * @ClassName： DefaultOAuth2AuthenticationEventPublisher.java
 * @Author： chenpan
 * @Date：2024/12/1 11:39
 * @Modify：
 */
public class DefaultOAuth2AuthenticationEventPublisher extends DefaultAuthenticationEventPublisher {

    public DefaultOAuth2AuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        super.publishAuthenticationFailure(convert(exception), authentication);
    }

    private AuthenticationException convert(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException authenticationException) {
            OAuth2Error error = authenticationException.getError();

            switch (error.getErrorCode()) {
                case OAuth2ErrorKeys.ACCOUNT_EXPIRED:
                    return new org.springframework.security.authentication.AccountExpiredException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.CREDENTIALS_EXPIRED:
                    return new CredentialsExpiredException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_DISABLED:
                    return new DisabledException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_LOCKED:
                    return new LockedException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.ACCOUNT_ENDPOINT_LIMITED:
                    return new AccountEndpointLimitedException(exception.getMessage(), exception.getCause());
                case OAuth2ErrorKeys.USERNAME_NOT_FOUND:
                    return new UsernameNotFoundException(exception.getMessage(), exception.getCause());
                default:
                    return new BadCredentialsException(exception.getMessage(), exception.getCause());
            }
        }
        return exception;
    }
}
