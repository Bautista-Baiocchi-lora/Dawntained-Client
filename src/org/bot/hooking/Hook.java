package org.bot.hooking;

import org.bot.Engine;
import org.bot.util.NetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ethan on 7/7/2017.
 */
public class Hook {

    private String HOOK_URL = null;
    private Map<String, FieldHook> fieldMap;
    private Map<String, MethodHook> methodMap;

    public Hook(String hookURL) {
        HOOK_URL = hookURL;
        init();
    }

    public void init() {
        /**
         * GRAB HOOKS FROM XML?
         */
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            URLConnection connection = NetUtil.createURLConnection(HOOK_URL);
            Document doc = dBuilder.parse(connection.getInputStream());
            doc.getDocumentElement().normalize();

            NodeList getters = doc.getElementsByTagName("add");

            for(int i = 0; i < getters.getLength(); i++) {
                Node n = getters.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String getter = e.getElementsByTagName("getter").item(0).getTextContent();
                    String clazz = e.getElementsByTagName("classname").item(0).getTextContent();
                    String field = e.getElementsByTagName("field").item(0).getTextContent();
                    String multiplier = e.getElementsByTagName("multiplier").item(0).getTextContent();
                    getFieldMap().put(getter, new FieldHook(clazz, field, Integer.parseInt(multiplier)));
                    //System.out.println("Getter: "+getter + " || Class: "+clazz + " || Field: "+field + " || Multiplier: "+multiplier);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClass(String getterName, boolean isField) {
        return isField ? getFieldMap().get(getterName).getClazz() : getMethodMap().get(getterName).getClazz();
    }

    public String getField(String getterName, boolean isField) {
        return isField ? getFieldMap().get(getterName).getField() : getMethodMap().get(getterName).getField();
    }

    public int getMuliplier(String getterName) {
        return getFieldMap().get(getterName).getMultiplier();
    }

    public String getDesc(String getterName) {
        return getMethodMap().get(getterName).getDesc();
    }

    public Map<String, FieldHook> getFieldMap() {
        return fieldMap == null ? fieldMap = new HashMap<>() : fieldMap;
    }
    public Map<String, MethodHook> getMethodMap() {
        return methodMap == null ? methodMap = new HashMap<>() : methodMap;
    }
}
