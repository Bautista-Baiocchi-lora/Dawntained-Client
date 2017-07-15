package org.bot.hooking;


import org.bot.Engine;
import org.bot.util.NetUtil;
import org.bot.util.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

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
        try {
            JSONObject mainObject = new JSONObject(NetUtil.readUrl(HOOK_URL));
            JSONObject resourceObject = mainObject.getJSONObject("resource");
            JSONArray hookArray = resourceObject.getJSONArray("getterInjects");
            for (int i = 0; i < hookArray.length(); i++) {
                final String getterName = hookArray.getJSONObject(i).getString("getterName");
                final String getterClassName = hookArray.getJSONObject(i).getString("getterClassName");
                final String getterFieldName = hookArray.getJSONObject(i).getString("getterFieldName");
                long multiplier = -1;
                if (!hookArray.getJSONObject(i).isNull("multiplier")) {
                    multiplier = hookArray.getJSONObject(i).getLong("multiplier");
                }
                if(!map.containsKey(getterName)) {
                    map.put(getterName, new FieldHook(getterClassName, getterFieldName, (int) multiplier));
                } else {
                    if(Utilities.inArray(getterName, importantNames)) {
                        duplicates++;
                        map.put(getterName+duplicates, new FieldHook(getterClassName, getterFieldName, (int) multiplier));
                    } else {
                        /**
                         * Handle duplicates we don't care about
                         */
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
