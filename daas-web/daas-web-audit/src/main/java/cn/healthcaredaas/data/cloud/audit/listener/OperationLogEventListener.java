package cn.healthcaredaas.data.cloud.audit.listener;

import cn.healthcaredaas.data.cloud.audit.event.OperationLogEvent;
import cn.healthcaredaas.data.cloud.audit.model.OperationLog;
import cn.healthcaredaas.data.cloud.audit.remote.ILogClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @ClassName： OperationLogEventListener.java
 * @Description: 操作日志事件监听器
 * @Author： chenpan
 * @Date：2025-06-06 21:26:06
 * @Modify：
 */
@Slf4j
public class OperationLogEventListener implements ApplicationListener<OperationLogEvent> {

    public OperationLogEventListener(ILogClient logClient) {
        this.logClient = logClient;
    }

    @Resource
    private ILogClient logClient;

    @Override
    @Async
    public void onApplicationEvent(OperationLogEvent event) {
        try {
            OperationLog log = (OperationLog) event.getSource();
            logClient.saveLog(log);
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }
}