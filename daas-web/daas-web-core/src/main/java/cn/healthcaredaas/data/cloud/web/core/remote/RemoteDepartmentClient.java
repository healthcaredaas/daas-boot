package cn.healthcaredaas.data.cloud.web.core.remote;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.core.constants.ServiceConstants;
import cn.healthcaredaas.data.cloud.web.core.dto.DepartmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**

 * @ClassName： RemoteDepartmentClient.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/13 14:26
 * @Modify：
 */
@FeignClient(value = ServiceConstants.SERVICE_MASTER_DATA, contextId = "md-department")
public interface RemoteDepartmentClient {

    /**
     * 根据科室ID集合获取科室主要信息
     *
     * @param ids
     * @return
     */
    @GetMapping("department/simple")
    RestResult<List<DepartmentDTO>> getDepartmentByIds(@RequestParam(value = "ids") List<String> ids);
}
