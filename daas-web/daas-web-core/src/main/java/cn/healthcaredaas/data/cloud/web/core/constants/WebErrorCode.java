package cn.healthcaredaas.data.cloud.web.core.constants;

import cn.healthcaredaas.data.cloud.core.constants.ErrorCode;

/**
 * <pre>Web 相关错误代码</pre>
 *
 * @ClassName： WebErrorCode.java
 * @Author： chenpan
 * @Date：2024/11/30 11:54
 * @Modify：
 */
public interface WebErrorCode extends ErrorCode {

    int FEIGN_DECODER_IO_EXCEPTION = WEB_MODULE_503_BEGIN + 1;
}
