package org.bot.hooking;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bot.Engine;
import org.bot.provider.manifest.HookType;
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
		fieldMap = new HashMap<>();
		methodMap = new HashMap<>();
		HOOK_URL = hookURL;
		init();
	}

	public final void init() {
		if(Engine.getServerManifest().hookType().equals(HookType.XML)) {
			new XMLParser(HOOK_URL, fieldMap);
		} else {
			new JSONParser(HOOK_URL, fieldMap);
		}

	}

	public final FieldHook getFieldHook(String getterName) {
		return fieldMap.get(getterName);
	}

	public final MethodHook getMethodHook(String getterName) {
		return methodMap.get(getterName);
	}

}
