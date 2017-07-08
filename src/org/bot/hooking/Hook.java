package org.bot.hooking;

import org.bot.Engine;

/**
 * Created by Ethan on 7/7/2017.
 */
public class Hook {
    private static Hook instance;
    private Engine engine = Engine.getInstance();
    public Hook() {
        init();
    }

    public void init() {
        /**
         * GRAB HOOKS FROM XML?
         */
        engine.getFieldMap().put("getGameX", new FieldHook("TestClass", "TestField", -1));
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

    public static Hook getInstance() {
        return instance == null ? instance = new Hook() : instance;
    }

}
