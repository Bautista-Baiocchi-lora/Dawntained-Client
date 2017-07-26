package org.bot.util;

import org.bot.util.directory.DirectoryManager;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bautistabaiocchi-lora on 7/25/17.
 */
public class ConfigManager {

	public static final String NAME = "config.properties";
	public final static String CONFIG_PATH = DirectoryManager.CACHE + File.separator + NAME;
	private final Properties config;

	public ConfigManager() {
		config = new Properties();
		try (FileInputStream inputStream = new FileInputStream(CONFIG_PATH)) {
			config.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveProperty(String key, String value) {
		config.put(key, value);
		save();
	}

	private void save() {
		try (FileOutputStream outputStream = new FileOutputStream(CONFIG_PATH)) {
			config.store(outputStream, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteProperty(String key) {
		config.remove(key);
		save();
	}

	public void deleteProperty(String key, String value) {
		config.remove(key, value);
		save();
	}

	public void updateProperty(String key, String value) {
		for (Map.Entry<Object, Object> entry : config.entrySet()) {
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}
		}
		save();
	}

	public String getProperty(String key) {
		return config.getProperty(key);
	}


}
