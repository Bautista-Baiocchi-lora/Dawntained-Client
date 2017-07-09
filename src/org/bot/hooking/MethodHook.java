package org.bot.hooking;

/**
 * Created by Ethan on 7/7/2017.
 */
public class MethodHook {
	private final String clazz;
	private final String field;
	private final String description;

	public MethodHook(String clazz, String field, String description) {
		this.clazz = clazz;
		this.field = field;
		this.description = description;

	}

	public String getClazz() {
		return clazz.replaceAll("/", ".");
	}

	public String getField() {
		return field.replaceAll("/", ".");
	}

	public String getDescription() {
		return description;
	}

}