package org.bot;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import org.bot.component.inputs.InternalKeyboard;
import org.bot.component.inputs.InternalMouse;
import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.reflection.ReflectionEngine;

public class Engine {

	public static final double VERSION = 0.13;
	private static String username;
	private static boolean developer;
	private static ServerLoader<?> serverLoader;
	private static Component gameComponent;
	private static boolean debugMouse;
	private static DirectoryManager directoryManager;
	private static ServerManifest serverManifest;
	private static Canvas gameCanvas;
	private static GameFrame gameFrame;
	private static ReflectionEngine reflectionEngine;
	private static Rectangle gameViewport = new Rectangle(5, 5, 509, 332);;
	private static InternalMouse mouse;
	private static InternalKeyboard keyboard;
	private static Map<String, String> providerJarNames = new HashMap();

	public static Map<String, String> getProviderJarNames() {
		return providerJarNames;
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

	public static void setDirectoryManager(DirectoryManager manager) {
		directoryManager = manager;
	}

	public static void setDeveloper(boolean developer) {
		Engine.developer = developer;
	}

	public static boolean isDeveloper() {
		return developer;
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
		serverLoader = provider.getLoader();
		serverManifest = provider.getManifest();
	}

	public static DirectoryManager getDirectoryManager() {
		return directoryManager;
	}

	public static Component getGameComponent() {
		return gameComponent;
	}

	public static void setGameComponent(Component gameComponent) {
		Engine.gameComponent = gameComponent;
	}

	public static Canvas getGameCanvas() {
		return gameCanvas;
	}

	public static void setGameCanvas(Canvas gameCanvas) {
		Engine.gameCanvas = gameCanvas;
	}

	public static String getInterfaceTitle() {
		return (username != null ? "[" + username + "] " : " ")
				+ (serverLoader != null ? (serverLoader.getServerName() + " || ") : "") + "uBot v" + VERSION;
	}
}
