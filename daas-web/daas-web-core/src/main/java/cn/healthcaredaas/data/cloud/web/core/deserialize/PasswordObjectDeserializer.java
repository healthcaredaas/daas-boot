package cn.healthcaredaas.data.cloud.web.core.deserialize;

import cn.healthcaredaas.data.cloud.web.core.password.codec.PasswordUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**

 * @ClassName： PasswordObjectDeserializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 16:09
 * @Modify：
 */
public class PasswordObjectDeserializer extends JsonDeserializer<JSONObject> {

    @Override
    public JSONObject deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = parser.getCodec().readTree(parser);
        String value = node.toString();
        if (value == null) {
            return null;
        }
        JSONObject json = JSON.parseObject(value);
        if (json.get(PasswordUtil.KEY_PASSWORD) != null) {
            json.put(PasswordUtil.KEY_PASSWORD, PasswordUtil.decode(json.getString(PasswordUtil.KEY_PASSWORD)));
        }
        return json;
    }
}
