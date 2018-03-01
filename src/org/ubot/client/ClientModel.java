package org.ubot.client;

import org.ubot.bot.Bot;
import org.ubot.bot.BotModel;
import org.ubot.bot.component.RSCanvas;
import org.ubot.client.provider.inputs.InternalKeyboard;
import org.ubot.client.provider.inputs.InternalMouse;
import org.ubot.bot.script.handler.ScriptHandler;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.client.account.Account;
import org.ubot.client.account.AccountManager;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.NullManifestException;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.client.ui.screens.loading.LoadingScreen;
import org.ubot.util.directory.DirectoryManager;
import org.ubot.util.directory.exceptions.InvalidDirectoryNameException;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClientModel {

	public static final double VERSION = 0.13;
	private final String username;
	private final AccountManager accountManager;
	private final boolean developer;
	private final Client client;
	private final HashMap<String, ServerProvider> serverProviders;
	private final ArrayList<Bot> bots;


	private static ServerProvider serverProvider;
	private static RSCanvas gameCanvas;
	private static Rectangle gameViewport = new Rectangle(5, 5, 509, 332);
	private static InternalMouse mouse;
	private static InternalKeyboard keyboard;
	private static Hashtable<Object, Object> modelCache = new Hashtable<>();
	private static ScriptHandler scriptHandler;


	public ClientModel(Client client, String username, String accountKey, boolean developer) {
		this.client = client;
		this.username = username;
		this.accountManager = new AccountManager(username, accountKey);
		this.developer = developer;
		this.serverProviders = loadServerProviders();
		this.bots = new ArrayList<>();
	}

	private final HashMap<String, ServerProvider> loadServerProviders() {
		final HashMap<String, ServerProvider> providers = new HashMap<>();
		try {
			for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory("Server Providers").getFiles()) {
				final ClassLoader classLoader = new URLClassLoader(new URL[] {file.toURI().toURL()});
				try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
					JarEntry jarEntry;
					while ((jarEntry = inputStream.getNextJarEntry()) != null) {
						if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
							String classPath = jarEntry.getName().replace(".class", "");
							Class<?> clazz = classLoader.loadClass(classPath);
							if (clazz.isAnnotationPresent(ServerManifest.class)) {
								final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
								if (manifest == null) {
									throw new NullManifestException();
								}
								ServerLoader<?> loader = (ServerLoader<?>) clazz.newInstance();
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
		Logger.log("Server Providers loaded.", LogType.CLIENT);
		return providers;
	}

	public HashMap<String, ServerProvider> getServerProviders() {
		return serverProviders;
	}

	public void launchServer(ServerProvider provider) {
		final LoadingScreen screen = new LoadingScreen(client, provider.getLoader());
		client.displayScreen(screen, "Launching server...");
		screen.run(new Account("user", "Server"), developer);
	}

	public void registerBot(BotModel.Builder modelBuilder) {
		this.bots.add(new Bot(modelBuilder));
	}

	public boolean isDeveloper() {
		return developer;
	}

	public String getUsername() {
		return username;
	}

}
