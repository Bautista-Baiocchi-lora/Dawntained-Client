package org.bot;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import org.bot.classloader.ArchiveClassLoader;
import org.bot.hooking.FieldHook;
import org.bot.hooking.MethodHook;
import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.BotUI;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.reflection.ReflectedClass;

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
	private Canvas gameCanvas;
	private ArchiveClassLoader classLoader;
	private Map<String, FieldHook> fieldMap;
	private Map<String, MethodHook> methodMap;

	public Map<String, FieldHook> getFieldMap() {
		return fieldMap == null ? fieldMap = new HashMap<>() : fieldMap;
	}
	public Map<String, MethodHook> getMethodMap() {
		return methodMap == null ? methodMap = new HashMap<>() : methodMap;
	}

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

	public Canvas getGameCanvas() {
		return gameCanvas;
	}

	public void setGameCanvas(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

	public ArchiveClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ArchiveClassLoader classLoader) {
		this.classLoader = classLoader;
	}
}
