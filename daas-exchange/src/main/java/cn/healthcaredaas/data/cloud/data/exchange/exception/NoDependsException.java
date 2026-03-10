package cn.healthcaredaas.data.cloud.data.exchange.exception;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @ClassName： NoDependsException.java
 * @Author： chenpan
 * @Date：2024/9/26 14:23
 * @Modify：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoDependsException extends PlatformException {

    private boolean retry;

    private int retryTimes;

    private String retryTopic;

    private String message;
}
