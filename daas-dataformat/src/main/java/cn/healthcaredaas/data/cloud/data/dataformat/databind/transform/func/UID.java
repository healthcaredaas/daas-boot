package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

/**
 * ************************
 * <p></p>
 * *************************
 *
 * @author chenpan
 * @date 2021/1/5 16:40
 */
@Component(value = "uid")
@TransFunction(code = "uid", name = "生成uuid", description = "系统生成无短横线的UUID")
public class UID implements ITransformFunction {
    @Override
    public boolean isRenderParams() {
        return false;
    }

    @Override
    public boolean withoutData() {
        return true;
    }

    @Override
    public String transform(Object params) {
        return IdUtil.fastSimpleUUID();
    }
}
