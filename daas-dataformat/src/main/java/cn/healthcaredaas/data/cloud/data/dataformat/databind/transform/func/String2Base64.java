package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * *******description*******
 * String 转Base64
 * *************************
 *
 * @author chenpan
 * @date 2020/1/6 12:19
 */
@Component(value = "string2Base64")
@TransFunction(code = "string2Base64", name = "字符串转Base64", description = "参数为数据集转换的字段")
public class String2Base64 implements ITransformFunction{

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
        if(params!=null){
            return Base64.encodeBase64String(params.toString().getBytes());
        }
        return null;
    }
}
