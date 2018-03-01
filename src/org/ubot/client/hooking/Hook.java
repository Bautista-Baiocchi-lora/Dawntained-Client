package org.ubot.client.hooking;

import java.util.HashMap;
import java.util.Map;

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
		new JSONParser(HOOK_URL, fieldMap);
	}

	public final FieldHook getFieldHook(String getterName) {
		return fieldMap.get(getterName);
	}

	public final MethodHook getMethodHook(String getterName) {
		return methodMap.get(getterName);
	}

}
