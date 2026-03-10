package cn.healthcaredaas.data.cloud.security.authorization.access;

import cn.healthcaredaas.data.cloud.security.authorization.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

/**
 * <pre>自定义AuthorizationManager，动态url权限判断</pre>
 *
 * @ClassName： CustomAuthorizationManager.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/9 16:36
 * @Modify：
 */
@Slf4j
@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private PermissionService permissionService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier,
                                       RequestAuthorizationContext authorizationContext) {

        Authentication authentication = authenticationSupplier.get();
        HttpServletRequest request = authorizationContext.getRequest();

        boolean isGranted = permissionService.hasPermission(authentication, request);
        return new AuthorizationDecision(isGranted);
    }


}
