package org.bot.hooking;


import org.bot.util.NetUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * Created by Ethan on 7/12/2017.
 */
public class JSONParser {
	private String HOOK_URL;
	private Map map;

	public JSONParser(String HOOK_URL, Map map) {
		this.HOOK_URL = HOOK_URL;
		this.map = map;
		init();
	}

	public void init() {
		try {
			org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
			JSONObject mainObject = (JSONObject) jsonParser.parse(NetUtil.readUrl(HOOK_URL));
			JSONObject resourceObject = (JSONObject) mainObject.get("resource");
			JSONArray hookArray = (JSONArray) resourceObject.get("getterInjects");
			for (Object hooks : hookArray) {
				JSONObject jsonHook = (JSONObject) hooks;
				final String getterName = (String) jsonHook.get("getterName");
				final String getterClassName = (String) jsonHook.get("getterClassName");
				final String getterFieldName = (String) jsonHook.get("getterFieldName");
				long multiplier = -1;
				if (jsonHook.get("multiplier") != null) {
					multiplier = (long) jsonHook.get("multiplier");
				}
				System.out.println("Getter: " + getterName + " || Class: " + getterClassName + " || Field: " + getterFieldName + " || Multiplier: " + multiplier);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
