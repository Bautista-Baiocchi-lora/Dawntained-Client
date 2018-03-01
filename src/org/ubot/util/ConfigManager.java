package org.ubot.util;

import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.util.directory.DirectoryManager;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bautistabaiocchi-lora on 7/25/17.
 */
public class ConfigManager {

	public static final String NAME = "config.properties";
	public static final String CONFIG_PATH = DirectoryManager.CACHE_PATH + File.separator + NAME;
	private static ConfigManager instance;
	private final Properties config;

	private ConfigManager() {
		config = new Properties();
		File file = new File(CONFIG_PATH);
		try {
			if (file.createNewFile()) {
				Logger.log("Config file created.", LogType.DEBUG);
			}
			FileInputStream inputStream = new FileInputStream(CONFIG_PATH);
			config.load(inputStream);
			inputStream.close();
			Logger.log("Config properties loaded.", LogType.DEBUG);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.logException("Error loading config.", LogType.DEBUG);
		}
		Logger.log("Config Manager started.", LogType.CLIENT);
	}

	public static void init() {
		instance = new ConfigManager();
	}

	public static ConfigManager getInstance() {
		return instance;
	}

	public void saveProperty(String key, String value) {
		config.put(key, value);
		Logger.log("Property pair: [" + key + "|" + value + "] saved.", LogType.DEBUG);
		save();
	}

	private void save() {
		try (FileOutputStream outputStream = new FileOutputStream(CONFIG_PATH)) {
			config.store(outputStream, null);
			Logger.log("Config saved.", LogType.DEBUG);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteProperty(String key) {
		config.remove(key);
		Logger.log("Property key: [" + key + "] deleted.", LogType.DEBUG);
		save();
	}

	public void deleteProperty(String key, String value) {
		config.remove(key, value);
		Logger.log("Property pair: [" + key + "|" + value + "] deleted.", LogType.DEBUG);
		save();
	}

	public void updateProperty(String key, String value) {
		for (Map.Entry<Object, Object> entry : config.entrySet()) {
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				Logger.log("Property pair: [" + key + "|" + value + "] updated.", LogType.DEBUG);
				return;
			}
		}
		save();
	}

	public String getProperty(String key) {
		return config.getProperty(key);
	}


}
