package cn.healthcaredaas.data.cloud.web.core.serialize;

import cn.healthcaredaas.data.cloud.web.core.password.codec.PasswordUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**

 * @ClassName： PasswordObjectSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 16:00
 * @Modify：
 */
public class PasswordObjectSerializer extends JsonSerializer<JSONObject> {


    @Override
    public void serialize(JSONObject value, JsonGenerator generator, SerializerProvider serializerProvider)
            throws IOException {
        if (value != null && value.get(PasswordUtil.KEY_PASSWORD) != null) {
            value.put(PasswordUtil.KEY_PASSWORD, PasswordUtil.encode(value.getString(PasswordUtil.KEY_PASSWORD)));
        }
        generator.writeObject(value);
    }
}
