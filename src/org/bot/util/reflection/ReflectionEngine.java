package org.bot.util.reflection;

import org.bot.classloader.ASMClassLoader;
import org.bot.classloader.ClassArchive;
import org.bot.hooking.FieldHook;
import org.bot.hooking.Hook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ReflectionEngine {

	private final Hook hooks;
	private ClassArchive path;
	private ASMClassLoader classLoader;

	public ReflectionEngine(ClassArchive path, Hook hooks) throws IOException {
		this.path = path;
		this.hooks = hooks;
		this.classLoader = new ASMClassLoader(path);
	}

	public ReflectedClass getClass(String name, Object instance) {
		try {
			return new ReflectedClass(classLoader.loadClass(name), instance);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	public ReflectedClass getClass(String name) {
		return getClass(name, null);
	}

	public ReflectedClass getClass(Object instance) {
		return getClass(instance.getClass().getSimpleName(), instance);
	}

	public String getClassName(String getter) {
		final FieldHook hook = hooks.getFieldHook(getter);
		return hook.getClazz();
	}

	public String getFieldName(String getter) {
		final FieldHook hook = hooks.getFieldHook(getter);
		return hook.getField();
	}

	public int getMultiplier(String getter) {
		final FieldHook hook = hooks.getFieldHook(getter);
		return hook.getMultiplier();
	}

	public ReflectedField getField(String getter, Object instance) {

		final ReflectedClass clazz = getClass(getClassName(getter), instance);
		final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(getFieldName(getter)).build());
		return field;
	}

	public ReflectedField getField(String getter) {
		return getField(getter, null);
	}

	public Object getFieldHookValue(String getter, Object instance) {
		try {
			final ReflectedClass clazz = getClass(getClassName(getter), instance);
			final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(getFieldName(getter)).build());
			if (getMultiplier(getter) != -1) {
				int decoded = ((int) field.getValue()) * getMultiplier(getter);
				return decoded;
			}
			return field.getValue();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getFieldHookValue(String getter) {
		return getFieldHookValue(getter, null);
	}

	public Object getMethodHookValue(String getter, Object... params) {
		final FieldHook hook = hooks.getFieldHook(getter);
		final ReflectedClass clazz = getClass(hook.getClazz());
		final ReflectedMethod method = clazz.getMethod(new Modifiers.ModifierBuilder().name(hook.getField()).build());
		try {
			return method.invoke(params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getMethodHookValue(String getter) {

		return getMethodHookValue(getter);
	}

}
