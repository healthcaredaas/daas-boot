package cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.transform.func.annotation.TransFunction;
import org.springframework.stereotype.Component;

/**
 * ************************
 * <p></p>
 * *************************
 *
 * @author chenpan
 * @date 2021/1/5 16:36
 */
@Component(value = "date")
@TransFunction(code = "date", name = "当前日期时间", description = "参数为日期时间格式化格式， 默认：yyyyMMddHHmmss")
public class Date implements ITransformFunction {

    public static final String DEFAULT_FORMAT = "yyyyMMddHHmmss";

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
        String format = DEFAULT_FORMAT;
        if (params != null && StrUtil.isBlank(params.toString())) {
            format = params.toString();
        }
        return DateUtil.format(new DateTime(), format);
    }
}
