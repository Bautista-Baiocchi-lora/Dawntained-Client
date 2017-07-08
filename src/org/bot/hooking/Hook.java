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

/**
 * Created by Ethan on 7/7/2017.
 */
public class Hook {

    private String HOOK_URL = null;
    private Engine engine = Engine.getInstance();

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

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList getters = doc.getElementsByTagName("add");
            for(int i = 0; i < getters.getLength(); i++) {
                Node n = getters.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String getter = e.getElementsByTagName("getter").item(0).getTextContent();
                    String clazz = e.getElementsByTagName("classname").item(0).getTextContent();
                    String field = e.getElementsByTagName("field").item(0).getTextContent();
                    String multiplier = e.getElementsByTagName("multiplier").item(0).getTextContent();
                    engine.getFieldMap().put(getter, new FieldHook(clazz, field, Integer.parseInt(multiplier)));
                    //System.out.println("Getter: "+getter + " || Class: "+clazz + " || Field: "+field + " || Multiplier: "+multiplier);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClass(String getterName, boolean isField) {
        return isField ? engine.getFieldMap().get(getterName).getClazz() : engine.getMethodMap().get(getterName).getClazz();
    }

    public String getField(String getterName, boolean isField) {
        return isField ? engine.getFieldMap().get(getterName).getField() : engine.getMethodMap().get(getterName).getField();
    }

    public int getMuliplier(String getterName) {
        return engine.getFieldMap().get(getterName).getMultiplier();
    }
    public String getDesc(String getterName) {
        return engine.getMethodMap().get(getterName).getDesc();
    }
}
