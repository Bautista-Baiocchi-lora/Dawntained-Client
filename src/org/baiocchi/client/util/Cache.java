package org.baiocchi.client.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Cache {

	public static final String CACHE_PATH = System.getProperty("user.home") + File.separator + "Dawntained Bot Client";
	public static final String JAR_URL = "http://dawntained.com/game/dawntained_client.jar";

	public static void revalidate() {
		create();
		if (!jarExists()) {
			downloadClientJar();
		}
	}

	private static boolean jarExists() {
		File jar = new File(getJarPath());
		return jar.exists();
	}

	private static void downloadClientJar() {
		System.out.println("Downloading client!");
		try (BufferedInputStream inStream = new BufferedInputStream(new URL(JAR_URL).openStream())) {
			try (BufferedOutputStream outStream = new BufferedOutputStream(
					new FileOutputStream(new File(CACHE_PATH + File.separator + "dawntainedClient.jar")))) {
				int bytesRead = 0;
				while ((bytesRead = inStream.read()) != -1) {
					outStream.write(bytesRead);
				}
				outStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Client downloaded!");
	}

	private static void create() {
		final File cache = new File(CACHE_PATH);
		if (!cache.exists()) {
			cache.mkdirs();
			System.out.println("Cache created!");
		}
	}

	public static String getJarPath() {
		return CACHE_PATH + File.separator + "dawntainedClient.jar";
	}

}
