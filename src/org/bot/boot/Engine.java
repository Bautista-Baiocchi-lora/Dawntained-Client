package org.bot.boot;

import java.io.File;

import javax.swing.JFrame;

import org.bot.loader.GameLoader;
import org.bot.util.directory.DirectoryManager;

public class Engine {

	private static Engine instance;
	private String username;
	private boolean developer;
	private GameLoader gameLoader;
	private JFrame gameJFrame;
	private boolean debugMouse;
	private DirectoryManager directoryManager;

	public void setGameLoader(GameLoader gameLoader) {
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

	public GameLoader getGameLoader() {
		return gameLoader;
	}

	public DirectoryManager getDirectoryManager() {
		return directoryManager;
	}

	public JFrame getGameJFrame() {
		return gameJFrame;
	}

	public void setGameJFrame(JFrame gameJFrame) {
		this.gameJFrame = gameJFrame;
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}
