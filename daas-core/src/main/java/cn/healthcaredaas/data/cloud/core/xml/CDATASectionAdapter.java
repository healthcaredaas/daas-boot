package cn.healthcaredaas.data.cloud.core.xml;

import cn.hutool.core.util.ObjectUtil;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @Description: JAXB 自定义CDATA XmlAdapter
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 17:03
 * @Modify：
 */
public class CDATASectionAdapter extends XmlAdapter<Object, Object> {
    @Override
    public String unmarshal(Object v) throws Exception {
        return v.toString();
    }

    @Override
    public String marshal(Object v) throws Exception {
        if (ObjectUtil.isNotNull(v)) {
            return "<![CDATA[" + v + "]]>";
        }
        return null;
    }
}