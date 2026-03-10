package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * *******description*******
 * byte数组转base64
 * *************************
 *
 * @author chenpan
 * @date 2020/1/6 12:25
 */
@Component(value = "byte2Base64")
@TransFunction(code = "byte2Base64", name = "byte数据转base64", description = "参数为数据集转换的字段")
public class Byte2Base64 implements ITransformFunction {


    @Override
    public boolean isRenderParams() {
        return false;
    }

    @Override
    public boolean withoutData() {
        return false;
    }

    @Override
    public String transform(Object params) {
        if (params != null && params instanceof byte[]) {
            return Base64.encodeBase64String((byte[]) params);
        }
        return null;
    }
}
