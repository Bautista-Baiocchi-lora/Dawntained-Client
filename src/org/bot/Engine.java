package org.bot;

import org.bot.account.AccountManager;
import org.bot.classloader.ClassArchive;
import org.bot.component.RSCanvas;
import org.bot.component.inputs.InternalKeyboard;
import org.bot.component.inputs.InternalMouse;
import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.script.handler.ScriptHandler;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.reflection.ReflectionEngine;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;

public class Engine {

	public static final double VERSION = 0.13;
	private static String username;
	private static boolean developer;
	private static ServerLoader<?> serverLoader;
	private static Component gameComponent;
	private static boolean debugMouse;
	private static DirectoryManager directoryManager;
	private static ServerManifest serverManifest;
	private static RSCanvas gameCanvas;
	private static GameFrame gameFrame;
	private static ReflectionEngine reflectionEngine;
	private static Rectangle gameViewport = new Rectangle(5, 5, 509, 332);
	private static InternalMouse mouse;
	private static InternalKeyboard keyboard;
	private static ClassArchive classArchive;
	private static Hashtable<Object, Object> modelCache = new Hashtable<>();
	private static ScriptHandler scriptHandler;
	private static HashMap<String, ServerProvider> serverProviders;
	private static AccountManager accountManager;

	public static HashMap<String, ServerProvider> getServerProviders() {
		return serverProviders;
	}

	public static void setServerProviders(HashMap<String, ServerProvider> providers) {
		Engine.serverProviders = providers;
	}

	public static Hashtable<Object, Object> getModelCache() {
		return modelCache;
	}

	public static ScriptHandler getScriptHandler() {
		if (scriptHandler == null)
			scriptHandler = new ScriptHandler();
		return scriptHandler;
	}

	public static URL toUrl(String path) {
		try {
			return new URL(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ClassArchive getClassArchive() {
		return classArchive;
	}

	public static void setClassArchive(ClassArchive classArchive) {
		Engine.classArchive = classArchive;
	}

	public static GameFrame getGameFrame() {
		return gameFrame;
	}

	public static void setGameFrame(GameFrame gameFrame) {
		Engine.gameFrame = gameFrame;
	}

	public static Rectangle getGameViewport() {
		return gameViewport;
	}

	public static void setGameViewport(Rectangle gameViewport) {
		Engine.gameViewport = gameViewport;
	}

	public static ReflectionEngine getReflectionEngine() {
		return reflectionEngine;
	}

	public static void setReflectionEngine(ReflectionEngine reflectionEngine) {
		Engine.reflectionEngine = reflectionEngine;
	}

	public static InternalMouse getMouse() {
		return mouse;
	}

	public static void setMouse(InternalMouse mouse) {
		Engine.mouse = mouse;
	}

	public static InternalKeyboard getKeyboard() {
		return keyboard;
	}

	public static void setKeyboard(InternalKeyboard keyboard) {
		Engine.keyboard = keyboard;
	}

	public static boolean isDeveloper() {
		return developer;
	}

	public static void setDeveloper(boolean developer) {
		Engine.developer = developer;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Engine.username = username;
	}

	public static boolean isDebugMouse() {
		return debugMouse;
	}

	public static void setDebugMouse(boolean debugMouse) {
		Engine.debugMouse = debugMouse;
	}

	public static ServerLoader<?> getServerLoader() {
		return serverLoader;
	}

	public static ServerManifest getServerManifest() {
		return serverManifest;
	}

	public static void setServerProvider(ServerProvider provider) {
		Engine.serverLoader = provider.getLoader();
		Engine.serverManifest = provider.getManifest();
	}

	public static DirectoryManager getDirectoryManager() {
		return directoryManager;
	}

	public static void setDirectoryManager(DirectoryManager manager) {
		Engine.directoryManager = manager;
	}

	public static Component getGameComponent() {
		return gameComponent;
	}

	public static void setGameComponent(Component gameComponent) {
		Engine.gameComponent = gameComponent;
	}

	public static RSCanvas getGameCanvas() {
		return gameCanvas;
	}

	public static void setGameCanvas(RSCanvas gameCanvas) {
		Engine.gameCanvas = gameCanvas;
	}

	public static void setAccountManager(AccountManager accountManager) {
		Engine.accountManager = accountManager;
	}

	public static AccountManager getAccountManager() {
		return accountManager;
	}

	public static String getInterfaceTitle() {
		return (username != null ? "[" + username + "] " : " ")
				+ (serverLoader != null ? (serverLoader.getServerName() + " || ") : "") + "uBot v" + VERSION;
	}
}
