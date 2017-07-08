package org.bot.util.reflection;

import org.bot.Engine;

/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ReflectionEngine {

	public ReflectedClass getClass(String name, Object instance) {
		if (!Engine.getInstance().getClassLoader().classes().containsKey(name)) {
			try {
				return new ReflectedClass(Engine.getInstance().getClassLoader().loadClass(name), instance);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new ReflectedClass(Engine.getInstance().getClassLoader().classes().get(name));
	}

	public ReflectedClass getClass(String name) {
		return getClass(name, null);
	}

	public ReflectedField getField(String className, String fieldName, Object instance) {
		ReflectedClass clazz;
		clazz = getClass(className, instance);
		return clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).isStatic(true).build());

	}

	public ReflectedField getField(String className, String fieldName) {
		ReflectedClass clazz;
		clazz = getClass(className);
		return clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
	}

}
