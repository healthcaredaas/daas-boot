package cn.healthcaredaas.data.cloud.security.authorization.remote;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.core.constants.ServiceConstants;
import cn.healthcaredaas.data.cloud.core.feign.Inner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**

 * @ClassName： RemoteAuthService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/13 15:21
 * @Modify：
 */
@FeignClient(value = ServiceConstants.SERVICE_FOUNDATION, contextId = "rbacResource")
public interface RemoteAuthService {

    @Inner
    @GetMapping("/rbac/resource/roles")
    RestResult<List<String>> authorityRoles(@RequestParam("url") String url,
                                            @RequestParam("method") String method);

    @Inner
    @GetMapping("/rbac/resource/map")
    RestResult<Map<String, String>> getRoleAuthorities(@RequestParam("role") String role);
}