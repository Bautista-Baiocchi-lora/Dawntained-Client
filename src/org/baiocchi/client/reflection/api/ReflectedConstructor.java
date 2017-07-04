package org.baiocchi.client.reflection.api;

import java.lang.reflect.Constructor;

public class ReflectedConstructor {

	private final Constructor<?> constructor;

	public ReflectedConstructor(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	public String getName() {
		return constructor.getName();
	}

}
