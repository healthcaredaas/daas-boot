package cn.healthcaredaas.data.cloud.data.hudi.client;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**

 * @ClassName： HudiField.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/30 11:01
 * @Modify：
 */
public class HudiSchema {

    public String name;
    private final JSONObject schema;
    private final List<JSONObject> fields;

    public HudiSchema(String name) {
        this.name = name;
        this.schema = new JSONObject();
        this.fields = new ArrayList<>();
    }

    public HudiSchema addField(String name, String type) {
        fields.add(field(name, type));
        return this;
    }

    public JSONObject build() {
        schema.put("type", "record");
        schema.put("name", name);
        schema.put("fields", fields);
        return schema;
    }

    private JSONObject field(String name, String type) {
        JSONObject field = new JSONObject();
        field.put("name", name);
        field.put("type", type);
        return field;
    }

}
