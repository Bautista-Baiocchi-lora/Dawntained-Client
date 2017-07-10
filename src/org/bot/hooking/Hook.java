package org.bot.hooking;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bot.util.NetUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Ethan on 7/7/2017.
 */
public class Hook {

	private final String HOOK_URL;
	private final Map<String, FieldHook> fieldMap;
	private final Map<String, MethodHook> methodMap;

	public Hook(String hookURL) {
		fieldMap = new HashMap<String, FieldHook>();
		methodMap = new HashMap<String, MethodHook>();
		HOOK_URL = hookURL;
		init();
	}

	public final void init() {
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
			for (int i = 0; i < getters.getLength(); i++) {
				Node n = getters.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					String getter = e.getElementsByTagName("getter").item(0).getTextContent();
					String clazz = e.getElementsByTagName("classname").item(0).getTextContent();
					String field = e.getElementsByTagName("field").item(0).getTextContent();
					String multiplier = e.getElementsByTagName("multiplier").item(0).getTextContent();
					fieldMap.put(getter, new FieldHook(clazz, field, Integer.parseInt(multiplier)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final FieldHook getFieldHook(String getterName) {
		return fieldMap.get(getterName);
	}

	public final MethodHook getMethodHook(String getterName) {
		return methodMap.get(getterName);
	}

}
