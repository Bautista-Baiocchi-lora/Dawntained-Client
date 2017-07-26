package org.bot.util.directory;

import org.bot.Engine;
import org.bot.classloader.ASMClassLoader;
import org.bot.classloader.ClassArchive;
import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.NullManifestException;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class DirectoryManager {

	public final static String BOT_DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "uBot";
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

	public DirectoryManager() {
		botDirectory = getRootDirectory();
		try {
			createSubDirectories();
		} catch (InvalidDirectoryNameException | IOException e) {
			e.printStackTrace();
		}
		Logger.log("Directory Manager started.", LogType.CLIENT);
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

	private void createSubDirectories() throws IOException, InvalidDirectoryNameException {
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

	public void loadServerProviderJars() {
		HashMap<String, ServerProvider> providers = new HashMap<String, ServerProvider>();
		try {
			for (File file : getRootDirectory().getSubDirectory("Server Providers")
					.getFiles()) {
				Engine.setClassArchive(new ClassArchive());
				Engine.getClassArchive().addJar((new File(file.getAbsolutePath()).toURI().toURL()));
				ASMClassLoader cl = new ASMClassLoader(Engine.getClassArchive());
				try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
					JarEntry jarEntry;
					while ((jarEntry = inputStream.getNextJarEntry()) != null) {
						if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
							Class<?> clazz;
							String classPackage = jarEntry.getName().replace(".class", "");
							clazz = cl.loadClass(classPackage.replaceAll("/", "."));
							ServerLoader<?> loader = null;
							if (clazz.isAnnotationPresent(ServerManifest.class)) {
								final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
								if (manifest == null) {
									throw new NullManifestException();
								}
								loader = (ServerLoader<?>) clazz.newInstance();
								providers.put(file.getName(), new ServerProvider(loader, manifest));
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (NullManifestException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InvalidDirectoryNameException e) {
			e.printStackTrace();
		}
		Engine.setServerProviders(providers);
		Logger.log("Server Providers loaded.", LogType.CLIENT);
	}

}
