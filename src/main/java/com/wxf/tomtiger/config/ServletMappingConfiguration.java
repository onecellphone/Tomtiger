package com.wxf.tomtiger.config;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wangxiaofeng
 * @version:
 * @date: 2021/9/3 18:13
 */
public class ServletMappingConfiguration {

    public static Map<String, String> servletMapping = new HashMap<>();

    public static void initServletMapping() throws DocumentException {
        File file = new File("config/action.xml");

        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        Element root = doc.getRootElement();

        List<Element> list = root.elements("listen");
        for (Element e : list) {
            Element action = e.element("action");
            if ("GET".equals(action.attributeValue("method").toUpperCase())) {
                servletMapping.put(action.getText(), e.elementText("target"));
            }
        }
    }

    public static void outputServletMapping() {
        for(String key : servletMapping.keySet()){
            System.out.println(String.format("url is %s, clazz is %s", key, servletMapping.get(key)));
        }
    }
}
