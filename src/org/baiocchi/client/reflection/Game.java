package org.baiocchi.client.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import javax.swing.JFrame;

import org.baiocchi.client.reflection.api.ReflectedClass;
import org.baiocchi.client.reflection.api.ReflectionEngine;
import org.baiocchi.client.util.Cache;

public class Game extends ReflectionEngine {

	public Game() throws MalformedURLException {
		super(Cache.getJarPath());
	}

	public JFrame getGameFrame() {
		Class<?> frameClass = loadClass("aw");
		JFrame frame = null;
		try {
			invokeMethod(frameClass.getSuperclass(), "main", 1, new Object[] { new String[] {} }, null);
			frame = (JFrame) getFieldValue(frameClass, "iW", null);
			System.out.println("Spoofing MAC address.");
			setField(frameClass.getSuperclass(), "iq", null, "68:a8:6e:3c:68:3c");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
		return frame;
	}

	public ReflectedClass getClass(String name) {
		return new ReflectedClass(loadClass(name));
	}

	public Object getFieldValue(Class<?> clazz, String name, Object instance)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(clazz, name);
		return field.get(instance);
	}

	public Field getField(Class<?> clazz, String name) throws NoSuchFieldException, SecurityException {
		Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}

	public void setField(Class<?> clazz, String name, Object instance, Object value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(clazz, name);
		field.set(instance, value);
	}

	public Object invokeMethod(Class<?> clazz, String name, int parameterCount, Object[] values, Object instance,
			Class<?> returnType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(name)) {
				if (method.getParameterCount() == parameterCount) {
					if (method.getReturnType().equals(returnType)) {
						if (values.length > 0) {
							for (int count = 0; count < parameterCount; count++) {
								Class<?> parameterClass = method.getParameterTypes()[count];
								if (!parameterClass.equals(values[count].getClass())) {
									continue;
								}
							}
							return method.invoke(instance, values);
						}
						return method.invoke(instance);
					}
				}
			}
		}
		return null;
	}

	public void invokeMethod(Class<?> clazz, String name, int parameterCount, Object[] values, Object instance)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		invokeMethod(clazz, name, parameterCount, values, instance, void.class);
	}

}
