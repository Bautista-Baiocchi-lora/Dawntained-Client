package org.bot.util.reflection;

import org.bot.Engine;

/**
 * Created by Ethan on 7/7/2017.
 */
public abstract class ReflectionEngine {

	public static ReflectedClass getClass(String name, Object instance) {
		if (!Engine.getClassLoader().classes().containsKey(name)) {
			try {
				return new ReflectedClass(Engine.getClassLoader().loadClass(name), instance);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new ReflectedClass(Engine.getClassLoader().classes().get(name));
	}

	public static ReflectedClass getClass(String name) {
		return getClass(name, null);
	}

	public static ReflectedField getField(String className, String fieldName, Object instance) {
		ReflectedClass clazz;
		clazz = getClass(className, instance);
		return clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).isStatic(true).build());

	}

	public static ReflectedField getField(String className, String fieldName) {
		ReflectedClass clazz;
		clazz = getClass(className);
		return clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
	}

	public static Object getFieldValue(String getter, Object instance)  {
		try {
			String className = Engine.getServerLoader().getHooks().getClass(getter, true);
			String fieldName = Engine.getServerLoader().getHooks().getField(getter, true);
			int multiplier = Engine.getServerLoader().getHooks().getMuliplier(getter);

			ReflectedField field = getField(className, fieldName, instance);
			if (multiplier != -1) {
				Integer decoded = (int) field.getValue() * Engine.getServerLoader().getHooks().getMuliplier(getter);
				return decoded;
			} else {
				return field.getValue();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValue(String getter)  {
		try {
			String className = Engine.getServerLoader().getHooks().getClass(getter, true);
			String fieldName = Engine.getServerLoader().getHooks().getField(getter, true);
			int multiplier = Engine.getServerLoader().getHooks().getMuliplier(getter);

			ReflectedField field = getField(className, fieldName);
			if (multiplier != -1) {
				Integer decoded = (int) field.getValue() * Engine.getServerLoader().getHooks().getMuliplier(getter);
				return decoded;
			} else {
				return field.getValue();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
