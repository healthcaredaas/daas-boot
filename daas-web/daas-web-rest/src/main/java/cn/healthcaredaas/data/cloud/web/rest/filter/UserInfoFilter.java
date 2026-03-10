package cn.healthcaredaas.data.cloud.web.rest.filter;

import cn.healthcaredaas.data.cloud.web.core.constants.SecurityConstants;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.context.UserContextHolder;
import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.web.core.utils.HeaderSecurityUtils;
import cn.healthcaredaas.data.cloud.web.rest.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * <pre>当前登录用户信息</pre>
 *
 * @ClassName： UserInfoFilter.java
 * @Author： chenpan
 * @Date：2024/12/16 15:36
 * @Modify：
 */
@Slf4j
public class UserInfoFilter extends OncePerRequestFilter {

    private UserInfoService userInfoService;

    public UserInfoFilter(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            String userId = HeaderSecurityUtils.getUserIdFromToken();
            if (StrUtil.isBlank(userId)) {
                userId = HeaderSecurityUtils.getUserId();
            }
            if (StrUtil.isNotBlank(userId)) {
                CurrentUserInfo userInfo = userInfoService.getUserInfo(userId);
                if (ObjectUtil.isNotNull(userInfo)) {
                    UserContextHolder.setCurrentUserInfo(userInfo);
                    request.setAttribute(SecurityConstants.USER_HEADER, userInfo.getUsername());
                    request.setAttribute(SecurityConstants.USER_ID_HEADER, userId);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("获取当前登录用户信息异常：{}", e.getMessage());
            throw new PlatformException(e);
        }
        UserContextHolder.clear();
    }
}
