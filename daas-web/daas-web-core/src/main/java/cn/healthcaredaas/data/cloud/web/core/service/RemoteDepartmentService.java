package cn.healthcaredaas.data.cloud.web.core.service;

import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.context.UserContextHolder;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.core.dto.DepartmentDTO;
import cn.healthcaredaas.data.cloud.web.core.remote.RemoteDepartmentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 * @ClassName： RemoteDepartmentService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/20 16:40
 * @Modify：
 */
@Service
public class RemoteDepartmentService {

    private static final Logger log = LoggerFactory.getLogger(RemoteDepartmentService.class);

    @Autowired(required = false)
    RemoteDepartmentClient departmentService;

    public List<DepartmentDTO> getDepartment(String userId) {
        try {
            CurrentUserInfo userInfo = UserContextHolder.getCurrentUserInfo();
            RestResult<List<DepartmentDTO>> result = departmentService.getDepartmentByIds(userInfo.getDeptIds());
            if (result.getSuccess()) {
                return result.getData();
            }
        } catch (Exception e) {
            log.error("获取用户科室信息异常：{}", e.getMessage());
        }

        return null;
    }
}
