package cn.healthcaredaas.data.cloud.security.core.event;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**

 * @ClassName： PlatformAuthenticationEvent.java
 * @Author： chenpan
 * @Date：2024/12/14 16:04
 * @Modify：
 */
public class PlatformAuthenticationEvent extends AbstractAuthenticationFailureEvent {

    public PlatformAuthenticationEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}
