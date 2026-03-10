package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser;

import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.DataTask;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

/**

 * @ClassName： AdapterSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/8 11:04
 * @Modify：
 */
public abstract class AdapterSerializer {

    @Setter
    @Getter
    private DataTask task;

    @Setter
    @Getter
    private LinkedHashMap<String, Object> data;

    public AdapterSerializer() {
    }

    public String serialize() {
        return null;
    }
}
