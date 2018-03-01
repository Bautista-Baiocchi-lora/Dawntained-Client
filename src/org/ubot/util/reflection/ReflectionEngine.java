package org.ubot.util.reflection;

import org.ubot.client.classloader.ASMClassLoader;
import org.ubot.client.hooking.FieldHook;
import org.ubot.client.hooking.Hook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ReflectionEngine {

	private final Hook hooks;
	private final ASMClassLoader classLoader;

	public ReflectionEngine(ASMClassLoader classLoader, Hook hooks) throws IOException {
		this.classLoader = classLoader;
		this.hooks = hooks;
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

	public Object getFieldValue(String className, String fieldName, Object instance) {
		try {
			final ReflectedClass clazz = getClass(className, instance);
			final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
			return field.getValue();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setFieldValue(String className, String fieldName, Object value, Object instance) {
		try {
			final ReflectedClass clazz = getClass(className, instance);
			final ReflectedField field = clazz.getField(new Modifiers.ModifierBuilder().name(fieldName).build());
			field.setValue(value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setFieldValue(String className, String fieldName, Object value) {
		setFieldValue(className, fieldName, value, null);
	}

	public Object getFieldValue(String className, String fieldName) {
		return getFieldValue(className, fieldName, null);
	}

	public Object getMethodValue(String className, String fieldName, int paramCount, String returnType, Object instance, Object... params) {
		try {
			final ReflectedClass clazz = getClass(className, instance);
			for (ReflectedMethod m : clazz.getMethods()) {
				if (m.getName().equals(fieldName)) {
					if (m.getParameterCount() == paramCount) {
						//Logger.log(m.getReturnType().toGenericString());
						if (m.getReturnType().toGenericString().equals(returnType)) {
							return m.invoke(params);
						}
					}
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getMethodHookValue(String getter) {

		return getMethodHookValue(getter);
	}

}
