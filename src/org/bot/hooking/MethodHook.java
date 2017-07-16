package org.bot.hooking;

/**
 * Created by Ethan on 7/7/2017.
 */
public class MethodHook {
	private final String clazz;
	private final String name;
	private final String description;

	public MethodHook(String clazz, String name, String description) {
		this.clazz = clazz;
		this.name = name;
		this.description = description;

	}

	public String getClazz() {
		return clazz.replaceAll("/", ".");
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}