package cn.healthcaredaas.data.cloud.audit.remote;

import cn.healthcaredaas.data.cloud.audit.model.OperationLog;
import cn.healthcaredaas.data.cloud.core.feign.Inner;
import cn.healthcaredaas.data.cloud.web.core.constants.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: TOOD
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/6/8 12:51
 * @Modify：
 */
@FeignClient(value = ServiceConstants.SERVICE_FOUNDATION, contextId = "logClient")
public interface ILogClient {

    @Inner
    @PostMapping("/audit/operationLog/event")
    void saveLog(@RequestBody OperationLog log);
}
