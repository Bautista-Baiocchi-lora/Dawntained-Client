package org.ubot.client;

import org.ubot.bot.Bot;
import org.ubot.bot.BotModel;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.account.Account;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.client.ui.BotTheater;
import org.ubot.client.ui.screens.BotLoadingScreen;
import org.ubot.util.directory.DirectoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClientModel {

	private final Client client;
	private final String username, accountKey, permissionKey;
	private final ArrayList<Bot> bots;
	private final BotTheater botTheater;

	public ClientModel(Client client, String username, String accountKey, String permissionKey) {
		this.client = client;
		this.username = username;
		this.accountKey = accountKey;
		this.permissionKey = permissionKey;
		this.bots = new ArrayList<>();
		this.botTheater = new BotTheater(client);
	}

	protected final BotTheater getUpdatedBotTheater() {
		botTheater.displayPreviews(bots);
		return botTheater;
	}

	protected final ArrayList<ServerProvider> getServerProviders() {
		final ArrayList<ServerProvider> providers = new ArrayList<>();
		providers.addAll(loadLocalServerProviders());
		providers.addAll(loadSDNServerProviders());
		return providers;
	}

	private final List<ServerProvider> loadSDNServerProviders() {
		final List<ServerProvider> providers = new ArrayList<>();
		return providers;
	}

	private final List<ServerProvider> loadLocalServerProviders() {
		final List<ServerProvider> providers = new ArrayList<>();
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
									providers.add(new ServerProvider(manifest, serverLoader));
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

	protected void loadServer(ServerLoader loader) {
		final BotLoadingScreen loadingScreen = new BotLoadingScreen(client, loader);
		client.displayScreen(loadingScreen);
		loadingScreen.run();
	}

	protected void createBot(BotModel.Builder builder) {
		final BotModel model = builder.account(new Account("Bautista", "Alora")).username(username).developer(true).build();
		final Bot bot = new Bot(model);
		bots.add(bot);
		client.showBotScreen(bot);
	}

}
