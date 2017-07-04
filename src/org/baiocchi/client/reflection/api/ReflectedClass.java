package org.baiocchi.client.reflection.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map.Entry;

public class ReflectedClass {

	private final Object instance;
	private final Class<?> clazz;

	public ReflectedClass(Class<?> clazz) {
		this(clazz, null);
	}

	public ReflectedClass(Class<?> clazz, Object instance) {
		this.clazz = clazz;
		this.instance = instance;
	}

	public String getName() {
		return clazz.getName();
	}

	public ReflectedField[] getFields() {
		final ArrayList<ReflectedField> fields = new ArrayList<ReflectedField>();
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				fields.add(new ReflectedField(field));
			}
		}
		if (instance != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					fields.add(new ReflectedField(field, instance));
				}
			}
		}
		return fields.toArray(new ReflectedField[fields.size()]);
	}

	public ReflectedField getField(Modifiers modifiers) {
		Fields: for (Field field : clazz.getDeclaredFields()) {
			for (Entry<Modifiers.Condition, ? extends Object> modifier : modifiers.getModifiers().entrySet()) {
				switch (modifier.getKey()) {
				case NAME:
					if (!field.getName().equals(modifier.getValue())) {
						continue Fields;
					}
					break;
				case STATIC:
					if (Modifier.isStatic(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case ABSTRACT:
					if (Modifier.isAbstract(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case TYPE:
					if (field.getType().equals((Class<?>) modifier.getValue())) {
						continue Fields;
					}
					break;
				case FINAL:
					if (Modifier.isFinal(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case VOLATILE:
					if (Modifier.isVolatile(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case PUBLIC:
					if (Modifier.isPublic(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case PRIVATE:
					if (Modifier.isPrivate(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				case PROTECTED:
					if (Modifier.isProtected(field.getModifiers()) != (boolean) modifier.getValue()) {
						continue Fields;
					}
					break;
				default:
					break;
				}
			}
			if (Modifier.isStatic(field.getModifiers())) {
				return new ReflectedField(field);
			}
			return new ReflectedField(field, instance);
		}
		return null;
	}

	public ReflectedMethod getMethod(Modifiers modifiers) {
		Methods: for (Method method : clazz.getDeclaredMethods()) {
			for (Entry<Modifiers.Condition, ? extends Object> modifier : modifiers.getModifiers().entrySet()) {
				switch (modifier.getKey()) {
				case NAME:
					if (!method.getName().equals(modifier.getValue())) {
						continue Methods;
					}
					break;
				case STATIC:
					if (Modifier.isStatic(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case ABSTRACT:
					if (Modifier.isAbstract(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case FINAL:
					if (Modifier.isFinal(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case VOLATILE:
					if (Modifier.isVolatile(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case PUBLIC:
					if (Modifier.isPublic(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case PRIVATE:
					if (Modifier.isPrivate(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case PROTECTED:
					if (Modifier.isProtected(method.getModifiers()) != (boolean) modifier.getValue()) {
						continue Methods;
					}
					break;
				case PARAMETER_TYPES:
					Class<?>[] parameters = (Class<?>[]) modifier.getValue();
					for (int count = 0; count < parameters.length; count++) {
						Class<?> parameterClass = method.getParameterTypes()[count];
						if (!parameterClass.equals(parameters[count].getClass())) {
							continue Methods;
						}
					}
					break;
				case PARAMETER_COUNT:
					if (method.getParameterCount() != (int) modifier.getValue()) {
						continue;
					}
					break;
				case RETURN_TYPE:
					if (!method.getReturnType().equals(modifier.getValue())) {
						continue Methods;
					}
					break;
				default:
					break;
				}
			}
			if (Modifier.isStatic(method.getModifiers())) {
				return new ReflectedMethod(method);
			}
			return new ReflectedMethod(method, instance);
		}
		return null;
	}

	public ReflectedMethod[] getMethods() {
		final ArrayList<ReflectedMethod> methods = new ArrayList<ReflectedMethod>();
		for (Method method : clazz.getDeclaredMethods()) {
			if (!Modifier.isStatic(method.getModifiers())) {
				methods.add(new ReflectedMethod(method));
			}
		}
		if (instance != null) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (Modifier.isStatic(method.getModifiers())) {
					methods.add(new ReflectedMethod(method, instance));
				}
			}
		}
		return methods.toArray(new ReflectedMethod[methods.size()]);
	}

	public ReflectedConstructor[] getConstructors() {
		final ArrayList<ReflectedConstructor> constructors = new ArrayList<ReflectedConstructor>();
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			constructors.add(new ReflectedConstructor(constructor));
		}
		return constructors.toArray(new ReflectedConstructor[constructors.size()]);
	}

}
