package cn.healthcaredaas.data.cloud.audit.event;

import cn.healthcaredaas.data.cloud.audit.model.OperationLog;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @ClassName： OperationLogEvent.java
 * @Description: 操作日志事件
 * @Author： chenpan
 * @Date：2025-06-06 21:26:06
 * @Modify：
 */
@Getter
public class OperationLogEvent extends ApplicationEvent {

    private final OperationLog operationLog;

    public OperationLogEvent(OperationLog source) {
        super(source);
        this.operationLog = source;
    }
}