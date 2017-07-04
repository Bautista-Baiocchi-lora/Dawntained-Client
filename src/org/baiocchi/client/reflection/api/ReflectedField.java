package org.baiocchi.client.reflection.api;

import java.lang.reflect.Field;

public class ReflectedField {

	private final Field field;
	private final Object instance;

	public ReflectedField(Field field) {
		this(field, null);
	}

	public ReflectedField(Field field, Object instance) {
		this.field = field;
		this.instance = instance;
	}

	public Object getValue() throws IllegalArgumentException, IllegalAccessException {
		return field.get(instance);
	}

	public void setValue(Object value) throws IllegalArgumentException, IllegalAccessException {
		field.set(instance, value);
	}
	
	public String getName() {
		return field.getName();
	}

}
