package org.bot.hooking;

import org.bot.util.NetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by Ethan on 7/12/2017.
 */
public class XMLParser {
    private String HOOK_URL;
    private Map map;
    public XMLParser(String HOOK_URL, Map map) {
        this.HOOK_URL = HOOK_URL;
        this.map = map;
        init();
    }
    public void init() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            URLConnection connection = NetUtil.createURLConnection(HOOK_URL);
            Document doc = dBuilder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();
            NodeList getters = doc.getElementsByTagName("add");
            for (int i = 0; i < getters.getLength(); i++) {
                Node n = getters.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String getter = e.getElementsByTagName("getter").item(0).getTextContent();
                    String clazz = e.getElementsByTagName("classname").item(0).getTextContent();
                    String field = e.getElementsByTagName("field").item(0).getTextContent();
                    String multiplier = e.getElementsByTagName("multiplier").item(0).getTextContent();
                    map.put(getter, new FieldHook(clazz, field, Integer.parseInt(multiplier)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
