package cn.healthcaredaas.data.cloud.web.core.deserialize;

import cn.hutool.core.util.BooleanUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**

 * @ClassName： NumericBooleanDeserializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/20 17:40
 * @Modify：
 */
public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return BooleanUtil.toBoolean(jsonParser.getText());
    }
}
