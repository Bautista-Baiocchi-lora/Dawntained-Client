package org.bot.hooking;


import org.bot.util.NetUtil;
import org.json.JSONArray;
import org.json.JSONObject;

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
			JSONObject mainObject = new JSONObject(NetUtil.readUrl(HOOK_URL));
			JSONObject resourceObject = mainObject.getJSONObject("resource");
			JSONArray hookArray = resourceObject.getJSONArray("getterInjects");
			for (int i = 0; i < hookArray.length(); i++) {
				final String getterName = hookArray.getJSONObject(i).getString("getterName");
				final String getterClassName = hookArray.getJSONObject(i).getString("getterClassName");
				final String getterFieldName = hookArray.getJSONObject(i).getString("getterFieldName");
				long multiplier = -1;
				if (hookArray.getJSONObject(i).get("multiplier") != null) {
					multiplier = hookArray.getJSONObject(i).getLong("multiplier");
				}
				System.out.println("Getter: " + getterName + " || Class: " + getterClassName + " || Field: " + getterFieldName + " || Multiplier: " + multiplier);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
