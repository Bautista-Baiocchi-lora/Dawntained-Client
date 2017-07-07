package org.bot.util.reflection;

import org.bot.Engine;
import org.bot.util.reflection.Modifiers;
/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ReflectionEngine {

    public ReflectedClass getClass(String name) {
        if (!Engine.getInstance().getClassLoader().classes().containsKey(name)) {
            try {
                return new ReflectedClass(Engine.getInstance().getClassLoader().loadClass(name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ReflectedClass(Engine.getInstance().getClassLoader().classes().get(name));
    }

    public ReflectedField getField(String className, String fieldName) {
        ReflectedClass clazz;
        clazz = getClass(className);
        return null;

    }
}
