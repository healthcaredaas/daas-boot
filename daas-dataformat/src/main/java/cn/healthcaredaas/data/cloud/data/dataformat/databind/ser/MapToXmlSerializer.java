package cn.healthcaredaas.data.cloud.data.dataformat.databind.ser;

import cn.hutool.core.util.XmlUtil;
import cn.healthcaredaas.data.cloud.data.dataformat.databind.schema.MessageSchemaDefinition;
import lombok.extern.slf4j.Slf4j;

/**

 * @ClassName： MapToXmlSerializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/6 09:40
 * @Modify：
 */
@Slf4j
public class MapToXmlSerializer extends AdapterSerializer {

    @Override
    public String serialize() {
        MessageSchemaDefinition definition = this.getTask().getDefinition();
        if (definition == null) {
            log.warn("缺少数据转换定义信息");
            return null;
        }
        return XmlUtil.mapToXmlStr(this.getData(), definition.getRoot());
    }
}
