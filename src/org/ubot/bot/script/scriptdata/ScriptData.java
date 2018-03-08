package org.ubot.bot.script.scriptdata;

import org.ubot.classloader.ClassArchive;

public class ScriptData {

	private final Class<?> clazz;
	private final String name, server, author, desc;
	private final double version;
	private final SkillCategory skillCategory;
	private final int scriptId;
	private final ClassArchive classArchive;

	public ScriptData(Class<?> clazz, ClassArchive classArchive, String name, String server, String desc, double version, String author, SkillCategory category) {
		this.clazz = clazz;
		this.name = name;
		this.server = server;
		this.desc = desc;
		this.version = version;
		this.author = author;
		this.skillCategory = category;
		this.scriptId = -1;
		this.classArchive = classArchive;
	}

	public ClassArchive getClassArchive() {
		return classArchive;
	}

	public Class<?> getMainClass() {
		return clazz;
	}

	public String getName() {
		return name;
	}

	public String getServer() {
		return server;
	}

	public String getDesc() {
		return desc;
	}

	public double getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

	public SkillCategory getSkillCategory() {
		return skillCategory;
	}

	public int getScriptId() {
		return scriptId;
	}
}