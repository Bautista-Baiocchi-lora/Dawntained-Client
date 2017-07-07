package org.bot;

import java.awt.Component;

import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.BotUI;
import org.bot.util.directory.DirectoryManager;

public class Engine {

	private static Engine instance;
	public static final double VERSION = 0.1;
	private String username;
	private boolean developer;
	private ServerLoader<?> serverLoader;
	private Component gameComponent;
	private boolean debugMouse;
	private DirectoryManager directoryManager;
	private ServerManifest serverManifest;
	private BotUI botUI;

	public void setBotUI(BotUI botUI) {
		this.botUI = botUI;
	}

	public void setDirectoryManager(DirectoryManager manager) {
		this.directoryManager = manager;
	}

	public void setDeveloper(boolean developer) {
		this.developer = developer;
	}

	public boolean getDeveloper() {
		return developer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isDebugMouse() {
		return debugMouse;
	}

	public void setDebugMouse(boolean debugMouse) {
		this.debugMouse = debugMouse;
	}

	public ServerLoader<?> getServerLoader() {
		return serverLoader;
	}

	public ServerManifest getServerManifest() {
		return serverManifest;
	}

	public void setServerProvider(ServerProvider provider) {
		this.serverLoader = provider.getLoader();
		this.serverManifest = provider.getManifest();
	}

	public DirectoryManager getDirectoryManager() {
		return directoryManager;
	}

	public Component getGameComponent() {
		return gameComponent;
	}

	public void setGameComponent(Component gameComponent) {
		this.gameComponent = gameComponent;
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}