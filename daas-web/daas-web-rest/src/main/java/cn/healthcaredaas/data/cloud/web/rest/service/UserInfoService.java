package cn.healthcaredaas.data.cloud.web.rest.service;

import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.rest.remote.RemoteUserService;
import lombok.extern.slf4j.Slf4j;

/**

 * @ClassName： UserInfoService.java
 * @Author： chenpan
 * @Date：2024/12/16 15:44
 * @Modify：
 */
@Slf4j
public class UserInfoService {

    private RemoteUserService remoteUserService;

    public UserInfoService(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    /**
     * 获取当前用户信息
     *
     * @param userId
     * @return
     */
    public CurrentUserInfo getUserInfo(String userId) {
        RestResult<CurrentUserInfo> result = remoteUserService.userInfo(userId);
        if (result.getSuccess()) {
            return result.getData();
        } else {
            log.error("获取当前用户信息异常:{}", result.getMessage());
        }
        return null;
    }
}
