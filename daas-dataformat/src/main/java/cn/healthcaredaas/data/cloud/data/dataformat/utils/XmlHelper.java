package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**

 * @ClassName： XmlHelper.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/9 14:17
 * @Modify：
 */
@Slf4j
public class XmlHelper {

    /**
     * 判断字符串是否xml文档
     *
     * @param str
     * @return
     */
    public static boolean isXmlDocument(String str) {
        boolean flag = true;
        try {
            StringReader sr = new StringReader(str);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(sr));
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
