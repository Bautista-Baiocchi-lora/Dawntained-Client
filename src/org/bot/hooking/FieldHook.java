package org.bot.hooking;

/**
 * Created by Ethan on 7/7/2017.
 */
public class FieldHook {
	private final String clazz;
	private final String field;
	private final int multiplier;

	public FieldHook(String clazz, String field, int multiplier) {
		this.clazz = clazz;
		this.field = field;
		this.multiplier = multiplier;
	}

	public String getClazz() {
		return clazz.replaceAll("/", ".");
	}

	public String getField() {
		return field.replaceAll("/", ".");
	}

	public int getMultiplier() {
		return multiplier;
	}

}
