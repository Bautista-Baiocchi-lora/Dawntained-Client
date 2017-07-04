package org.baiocchi.client.reflection.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectedMethod {
	private final Method method;
	private final Object instance;

	public ReflectedMethod(Method method, Object instance) {
		this.method = method;
		this.instance = instance;
	}

	public ReflectedMethod(Method method) {
		this(method, null);
	}

	public Object invoke(Object... parameters)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return method.invoke(instance, parameters);
	}

	public String getName() {
		return method.getName();
	}

}
