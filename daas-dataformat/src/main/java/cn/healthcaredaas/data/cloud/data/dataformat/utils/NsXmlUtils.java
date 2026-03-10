package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import cn.hutool.core.map.MapUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author chenpan
 * @date 2021/1/6 10:16
 */
public class NsXmlUtils {

    public static Document loadDocument(String xmlStr)
            throws DocumentException {
        SAXReader saxReader = new SAXReader();
        return saxReader.read(new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)));
    }

    public static List<Node> getNode(Document doc, Map<String, String> nsMap, String xpath) {
        if (MapUtil.isNotEmpty(nsMap)) {
            XPath x = doc.createXPath(xpath);
            x.setNamespaceURIs(nsMap);
            return x.selectNodes(doc);
        }
        return doc.selectNodes(xpath);
    }

    public static Node getSingleNode(Document doc, Map<String, String> nsMap, String xpath) {
        if (MapUtil.isNotEmpty(nsMap)) {
            XPath x = doc.createXPath(xpath);
            x.setNamespaceURIs(nsMap);
            return x.selectSingleNode(doc);
        }
        return doc.selectSingleNode(xpath);
    }
}
