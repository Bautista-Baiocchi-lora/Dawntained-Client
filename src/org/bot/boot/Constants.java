package org.bot.boot;

import java.io.File;

public class Constants {

	public static final String CACHE_PATH = System.getProperty("user.home") + File.separator + "ReflectionBot"
			+ File.separator;
	public static final String JAR_URL = "https://www.dropbox.com/s/b1ln699519xcvgg/TEST.jar?dl=1";

	public static String getJarPath() {
		return CACHE_PATH + File.separator + "GamePack.jar";
	}

}
