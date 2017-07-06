package org.bot.boot;

import java.awt.Component;

import org.bot.loader.ServerLoader;
import org.bot.util.directory.DirectoryManager;

public class Engine {

	private static Engine instance;
	private String username;
	private boolean developer;
	private ServerLoader<?> gameLoader;
	private Component gameComponent;
	private boolean debugMouse;
	private DirectoryManager directoryManager;

	public void setGameLoader(ServerLoader<? extends Component> gameLoader) {
		this.gameLoader = gameLoader;
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

	public ServerLoader<?> getGameLoader() {
		return gameLoader;
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
