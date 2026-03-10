package cn.healthcaredaas.data.cloud.web.rest.feign;

import cn.healthcaredaas.data.cloud.core.jackson.JacksonUtils;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.web.core.exception.FeignDecodeIOException;
import com.fasterxml.jackson.databind.JavaType;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import kotlin.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <pre>Feign 错误信息解码器</pre>
 *
 * @ClassName： FeignErrorDecoder.java
 * @Author： chenpan
 * @Date：2024/11/30 11:51
 * @Modify：
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String content = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            RestResult<String> result = RestResult.failure("Feign 远程调用" + methodKey + " 出错");
            JavaType javaType = JacksonUtils.getTypeFactory().constructParametricType(Result.class, String.class);
            RestResult<String> object = JacksonUtils.toObject(content, javaType);
            if (ObjectUtils.isEmpty(object)) {
                result = object;
            }
            return new FeignRemoteCallExceptionWrapper(result);
        } catch (IOException e) {
            log.error("[DaaS] Feign invoke [{}] error decoder convert result catch io exception.", methodKey, e);
            return new FeignDecodeIOException();
        }
    }
}
