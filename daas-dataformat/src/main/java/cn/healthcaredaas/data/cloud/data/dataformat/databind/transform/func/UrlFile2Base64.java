package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * *******description*******
 * Url文件转base64
 * *************************
 *
 * @author chenpan
 * @date 2020/1/6 12:28
 */
@Slf4j
@Component(value = "urlFile2Base64")
@TransFunction(code = "urlFile2Base64", name = "根据URL获取文件并转为Base64", description = "参数为URL地址，可使用数据集中的字段替换,例：${id}")
public class UrlFile2Base64 implements ITransformFunction {

    @Override
    public boolean isRenderParams() {
        return true;
    }

    @Override
    public boolean withoutData() {
        return false;
    }

    @Override
    public String transform(Object params) {

        try {
            byte[] bytes = HttpRequest.get(params.toString()).execute().bodyBytes();
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("urlFile2Base64转换异常：{}", e.getMessage());
        }
        return null;
    }
}
