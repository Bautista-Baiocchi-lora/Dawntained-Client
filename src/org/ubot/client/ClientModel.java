package org.ubot.client;

import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.account.Account;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.client.ui.loading.LoadingScreen;
import org.ubot.util.directory.DirectoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClientModel {

	private final Client client;
	private final String username, accountKey, permissionKey;

	public ClientModel(Client client, String username, String accountKey, String permissionKey) {
		this.client = client;
		this.username = username;
		this.accountKey = accountKey;
		this.permissionKey = permissionKey;
	}

	protected final HashMap<String, ServerProvider> getLocalServerProviders() {
		final HashMap<String, ServerProvider> providers = new HashMap<>();
		try {
			for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory(DirectoryManager.SERVER_PROVIDERS).getFiles()) {
				final ClassArchive classArchive = new ClassArchive();
				if (file.getAbsolutePath().endsWith(".jar")) {
					classArchive.addJar(file);
					final ASMClassLoader classLoader = new ASMClassLoader(classArchive);
					try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
						JarEntry jarEntry;
						while ((jarEntry = inputStream.getNextJarEntry()) != null) {
							if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
								String classPackage = jarEntry.getName().replace(".class", "");
								Class<?> clazz = classLoader.loadClass(classPackage.replaceAll("/", "."));
								if (clazz.isAnnotationPresent(ServerManifest.class)) {
									System.out.println("Loading Server");
									final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
									final ServerLoader serverLoader = (ServerLoader) clazz.newInstance();
									providers.put(manifest.serverName(), new ServerProvider(manifest, serverLoader));
									System.out.println("Server Loaded: " + manifest.serverName());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return providers;
	}

	protected void createBot(ServerLoader loader) {
		final LoadingScreen loadingScreen = new LoadingScreen(client, loader);
		client.displayScreen(loadingScreen, "Starting BotModel...");
		loadingScreen.run(new Account("Bautista", "Alora"), username, permissionKey);
	}

}
