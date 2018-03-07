package org.ubot.util.reflection;

import java.util.HashMap;

public class Modifiers {

	private final HashMap<Condition, ? extends Object> modifiers;

	private Modifiers(Builder builder) {
		this.modifiers = builder.modifiers;
	}

	public HashMap<Condition, ? extends Object> getModifiers() {
		return modifiers;
	}

	public static enum Condition {
		STATIC, ABSTRACT, NAME, PARAMETER_TYPES, PARAMETER_COUNT, RETURN_TYPE, VOLATILE, FINAL, TYPE, PUBLIC, PRIVATE, PROTECTED;
	}

	public static class Builder {

		private final HashMap<Condition, Object> modifiers;

		public Builder() {
			modifiers = new HashMap<Condition, Object>();
		}

		public Builder name(String name) {
			modifiers.put(Condition.NAME, name);
			return this;
		}

		public Builder type(Class<?> type) {
			modifiers.put(Condition.TYPE, type);
			return this;
		}

		public Builder isStatic(boolean isStatic) {
			modifiers.put(Condition.STATIC, isStatic);
			return this;
		}

		public Builder isAbstract(boolean isAbstract) {
			modifiers.put(Condition.ABSTRACT, isAbstract);
			return this;
		}

		public Builder returnType(Class<?> returnType) {
			modifiers.put(Condition.RETURN_TYPE, returnType);
			return this;
		}

		public Builder parameterTypes(Class<?>... parameterTypes) {
			modifiers.put(Condition.PARAMETER_COUNT, parameterTypes.length);
			modifiers.put(Condition.PARAMETER_TYPES, parameterTypes);
			return this;
		}

		public Builder parameterCount(int count) {
			modifiers.put(Condition.PARAMETER_COUNT, count);
			return this;
		}

		public Builder isVolatile(boolean isVolatile) {
			modifiers.put(Condition.VOLATILE, isVolatile);
			return this;
		}

		public Builder isFinal(boolean isFinal) {
			modifiers.put(Condition.FINAL, isFinal);
			return this;
		}

		public Modifiers build() {
			return new Modifiers(this);
		}
	}

}