package cn.healthcaredaas.data.cloud.web.rest.remote;

import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.core.constants.ServiceConstants;
import cn.healthcaredaas.data.cloud.core.feign.Inner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**

 * @ClassName： RemoteUserService.java
 * @Author： chenpan
 * @Date：2024/12/16 16:13
 * @Modify：
 */
@FeignClient(value = ServiceConstants.SERVICE_FOUNDATION, contextId = "rbacUser")
public interface RemoteUserService {

    @Inner
    @GetMapping("/rbac/user/info")
    RestResult<CurrentUserInfo> userInfo(@RequestParam("userId") String userId);
}
