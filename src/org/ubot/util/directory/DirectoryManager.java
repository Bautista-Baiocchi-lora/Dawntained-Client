package org.ubot.util.directory;


import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.IOException;

public class DirectoryManager {

	public final static String BOT_DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "uBot";
	private static DirectoryManager instance;
	public final static String CACHE = "Cache";
	public final static String SERVER_PROVIDERS = "Server Providers";
	public final static String SCRIPTS = "Scripts";
	public final static String TEMP = "Temp";
	public final static String SERVER_JARS = "Server Jars";
	public final static String SCREENSHOTS = "Screenshots";
	public final static String ACCOUNTS = "Accounts";
	public static String ACCOUNTS_PATH;
	public static String CACHE_PATH;
	public static String SERVER_PROVIDERS_PATH;
	public static String SCRIPTS_PATH;
	public static String TEMP_PATH;
	public static String SERVER_JARS_PATH;
	public static String SCREENSHOTS_PATH;
	private final Directory botDirectory;

	private DirectoryManager() {
		botDirectory = getRootDirectory();
		try {
			validateSubDirectories();
		} catch (InvalidDirectoryNameException | IOException e) {
			e.printStackTrace();
		}
		Logger.log("Directory Manager started.", LogType.CLIENT);
	}

	public static void init() {
		instance = new DirectoryManager();
	}

	public static DirectoryManager getInstance() {
		return instance;
	}

	public Directory getRootDirectory() {
		final Directory directory = new Directory(BOT_DIRECTORY_PATH);
		if (!directory.exists()) {
			try {
				if (directory.create()) {
					return directory;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return directory;
	}

	private void validateSubDirectories() throws IOException, InvalidDirectoryNameException {
		botDirectory.createSubDirectory(CACHE);
		CACHE_PATH = botDirectory.getSubDirectory(CACHE).getPath();
		botDirectory.createSubDirectory(SERVER_PROVIDERS);
		SERVER_PROVIDERS_PATH = botDirectory.getSubDirectory(SERVER_PROVIDERS).getPath();
		botDirectory.createSubDirectory(SCRIPTS);
		SCRIPTS_PATH = botDirectory.getSubDirectory(SCRIPTS).getPath();
		botDirectory.getSubDirectory(CACHE).createSubDirectory(SERVER_JARS);
		SERVER_JARS_PATH = botDirectory.getSubDirectory(CACHE).getSubDirectory(SERVER_JARS).getPath();
		botDirectory.getSubDirectory(CACHE).createSubDirectory(TEMP);
		TEMP_PATH = botDirectory.getSubDirectory(CACHE).getSubDirectory(TEMP).getPath();
		botDirectory.getSubDirectory(CACHE).createSubDirectory(SCREENSHOTS);
		SCREENSHOTS_PATH = botDirectory.getSubDirectory(CACHE).getSubDirectory(SCREENSHOTS).getPath();
		botDirectory.getSubDirectory(CACHE).createSubDirectory(ACCOUNTS);
		ACCOUNTS_PATH = botDirectory.getSubDirectory(CACHE).getSubDirectory(ACCOUNTS).getPath();
		Logger.log("uBot directories created.", LogType.CLIENT);
	}

}
