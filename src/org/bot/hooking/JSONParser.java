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
			JSONObject obj = new JSONObject(NetUtil.readUrl(HOOK_URL));
			JSONArray jsonArray = obj.getJSONArray("getterInjects");
			for (int i = 0; i < jsonArray.length(); i++) {
				String className = jsonArray.getJSONObject(i).getString("className");
				String getterName = jsonArray.getJSONObject(i).getString("getterName");

				System.out.println(getterName + " : " + className);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
