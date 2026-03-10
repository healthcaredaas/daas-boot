package cn.healthcaredaas.data.cloud.data.hudi.client;

import com.alibaba.fastjson2.JSONObject;
import org.apache.hudi.common.model.HoodieTableType;
import org.junit.jupiter.api.Test;

/**

 * @ClassName： HudiClientTest.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/30 10:47
 * @Modify：
 */
public class HudiClientTest {

    String hdfsPath = "hdfs://localhost:9000/hudi/hdw";

    public JSONObject buildSchema() {
        return new HudiSchema("patient")
                .addField("id", "string")
                .addField("patient_id", "string")
                .addField("patient_name", "string")
                .addField("gender_code", "string")
                .addField("gender_name", "string")
                .addField("birthdate", "string")
                .addField("social_no", "string")
                .build();
    }

    public JSONObject buildData() {
        JSONObject data = new JSONObject();
        data.put("id", "1");
        data.put("patient_id", "100001");
        data.put("patient_name", "测试1");
        data.put("gender_code", "1");
        data.put("gender_name", "男性");
        data.put("birthdate", "1990-10-10");
        data.put("social_no", "11111111111111");
        return data;
    }

    @Test
    public void insert() {
        JSONObject schema = buildSchema();
        HudiClient client = new HudiClient();
        client.init(hdfsPath, "patient", schema.toJSONString(), "id", null, HoodieTableType.COPY_ON_WRITE);
        client.insert(buildData());
        client.close();
    }

    @Test
    public void upsert() {
    }

    @Test
    public void delete() {
    }
}