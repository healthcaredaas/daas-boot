package cn.healthcaredaas.data.cloud.core.exception;

import cn.healthcaredaas.data.cloud.core.rest.Feedback;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;

/**

 * @ClassName： DaaSException.java
 * @Author： chenpan
 * @Date：2024/11/28 15:42
 * @Modify：
 */
public interface DaaSException {

    /**
     * 获取反馈信息
     *
     * @return 反馈信息对象 {@link Feedback}
     */
    Feedback getFeedback();

    /**
     * 错误信息转换为 R 对象。
     *
     * @return 结果对象 {@link RestResult}
     */
    RestResult<String> getResult();
}
