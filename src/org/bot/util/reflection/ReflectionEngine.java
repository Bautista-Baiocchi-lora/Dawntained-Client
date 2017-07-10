package org.bot.util.reflection;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.hooking.FieldHook;
import org.bot.hooking.Hook;
import org.bot.hooking.MethodHook;

public class ReflectionEngine extends ArchiveClassLoader {

	private final Hook hooks;

	public ReflectionEngine(Archive<?> archive, Hook hooks) throws IOException {
		super(archive);
		this.hooks = hooks;
	}

	public ReflectedClass getClass(String name, Object instance) {
		if (!this.classes().containsKey(name)) {
			try {
				return new ReflectedClass(this.loadClass(name), instance);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new ReflectedClass(this.classes().get(name), instance);
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

	public Object getMethodHookValue(String getter, Object instance) {
		final MethodHook hook = hooks.getMethodHook(getter);
		final ReflectedClass clazz = getClass(hook.getClazz(), instance);
		final ReflectedMethod method = clazz.getMethod(new Modifiers.ModifierBuilder().name(hook.getName()).build());
		try {
			return method.invoke();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getMethodHookValue(String getter) {
		return getMethodHookValue(getter, null);
	}

}
