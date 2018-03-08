package org.ubot.util.reflection;

import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ReflectionEngine {

	private ClassArchive path;
	private ASMClassLoader classLoader;

	public ReflectionEngine(ASMClassLoader classLoader) throws IOException {
		this.path = path;
		this.classLoader = classLoader;
	}

	public ReflectedClass getClass(String name, Object instance) {
		try {
			return new ReflectedClass(classLoader.loadClass(name), instance);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ASMClassLoader getClassLoader() {
		return classLoader;
	}

	public ReflectedClass getClass(String name) {
		return getClass(name, null);
	}

	public ReflectedClass getClass(Object instance) {
		return getClass(instance.getClass().getSimpleName(), instance);
	}


	public ReflectedField getField(String className, String fieldName, Object instance) {
		final ReflectedClass clazz = getClass(className, instance);
		final ReflectedField field = clazz.getField(new Modifiers.Builder().name(fieldName).build());
		return field;
	}

	public void setFieldValue(String className, String fieldName, Object value, Object instance) {
		try {
			final ReflectedClass clazz = getClass(className, instance);
			final ReflectedField field = clazz.getField(new Modifiers.Builder().name(fieldName).build());
			System.out.println(field.getType().toGenericString());
			field.setValue(value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setScriptReflEngine(String className, Object value) {
		try {
			final ReflectedClass clazz = getClass(className);
			for (ReflectedField f : clazz.getFields()) {
				if (f.getType().toGenericString().equals("public class org.ubot.util.reflection.ReflectionEngine")) {
					f.setValue(value);
				}
			}

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public void setFieldValue(String className, String fieldName, Object value) {
		setFieldValue(className, fieldName, value, null);
	}

	public ReflectedField getField(String className, String fieldName) {
		return getField(className, fieldName, null);
	}

	public ReflectedMethod getMethod(String className, String methodName, Object instance) {

		final ReflectedClass clazz = getClass(className, instance);
		final ReflectedMethod method = clazz.getMethod(new Modifiers.Builder().name(methodName).build());
		return method;
	}

	public ReflectedMethod getMethod(String className, String methodName) {
		return getMethod(className, methodName, null);
	}

	public Object getFieldValue(String className, String fieldName, Object instance) {
		try {
			final ReflectedClass clazz = getClass(className, instance);
			final ReflectedField field = clazz.getField(new Modifiers.Builder().name(fieldName).build());
			return field.getValue();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
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

	public Object getMethodHookValue(String className, String fieldName, int pc, Object... params) {
		try {
			final ReflectedClass clazz = getClass(className);
			for (ReflectedMethod m : clazz.getMethods()) {
				if (m != null) {
					if (m.getName().equals(fieldName)) {
						if (m.getParameterCount() == pc) {
							return m.invoke(params);
						}
					}
				}
			}

			return null;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getMethodHookValue(String getter) {

		return getMethodHookValue(getter);
	}


}
