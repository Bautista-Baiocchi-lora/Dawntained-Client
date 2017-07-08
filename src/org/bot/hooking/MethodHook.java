package org.bot.hooking;

/**
 * Created by Ethan on 7/7/2017.
 */
public class MethodHook {
	private String clazz;
	private String field;
	private String desc;

	public MethodHook(String clazz, String field, String desc) {
		this.clazz = clazz;
		this.field = field;
		this.desc = desc;

	}

	public String getClazz() {
		return clazz.replaceAll("/", ".");
	}

	public String getField() {
		return field.replaceAll("/", ".");
	}

	public String getDesc() {
		return desc;
	}

}