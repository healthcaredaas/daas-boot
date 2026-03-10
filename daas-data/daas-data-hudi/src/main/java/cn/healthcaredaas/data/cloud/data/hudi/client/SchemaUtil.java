package cn.healthcaredaas.data.cloud.data.hudi.client;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**

 * @ClassName： SchemaUtil.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/30 10:51
 * @Modify：
 */
public class SchemaUtil {

    public static JSONObject schema(String name, List<JSONObject> fields) {
        JSONObject schema = new JSONObject();
        schema.put("type", "record");
        schema.put("name", name);
        schema.put("fields", fields);
        return schema;
    }

    public static JSONObject field(String name, String type) {
        JSONObject field = new JSONObject();
        field.put("name", name);
        field.put("type", type);
        return field;
    }


}
