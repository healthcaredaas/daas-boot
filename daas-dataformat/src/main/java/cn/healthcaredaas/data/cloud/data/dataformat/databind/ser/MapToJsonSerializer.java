package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

/**

 * @ClassName： MapToJsonSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/6 09:39
 * @Modify：
 */
public class MapToJsonSerializer extends AdapterSerializer {

    @Override
    public String serialize() {
        return JSONObject.toJSONString(this.getData(), JSONWriter.Feature.WriteMapNullValue);
    }
}
