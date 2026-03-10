package cn.healthcaredaas.data.cloud.core.xml;

import com.sun.xml.txw2.output.CharacterEscapeHandler;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.io.StringWriter;

/**
 * @Description: Jaxb工具类
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2025/7/8 17:31
 * @Modify：
 */
@Slf4j
public class JaxbUtils {
    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     *
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            // 不进行转义字符的处理
            marshaller.setProperty(CharacterEscapeHandler.class.getName(), (CharacterEscapeHandler)
                    (ch, start, length, isAttVal, writer) -> writer.write(ch, start, length));

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            log.error("JavaBean转换成xml异常", e);
        }

        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml
     * @param c
     * @return
     */
    public static <T> T convertToBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            log.error("xml转换成JavaBean异常", e);
        }
        return t;
    }
}
