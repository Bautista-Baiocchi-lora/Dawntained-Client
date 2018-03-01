package org.ubot.client.hooking;


import java.util.Map;

/**
 * Created by Ethan on 7/12/2017.
 */
public class JSONParser {
	private String HOOK_URL;
	private Map map;
	private int duplicates = 0;
	private String[] importantNames = {"getX", "getItemIds", "getItemId", "getZ", "getY", "getName", "getId", "getActions", "getWidth", "getHeight"
			, "getRenderable", "getPlane", "getHeight", "getOrientation", "getCombatLevel", "getObjects"};

	public JSONParser(String HOOK_URL, Map map) {
		this.HOOK_URL = HOOK_URL;
		this.map = map;
		init();
	}

	public void init() {

	}

}
