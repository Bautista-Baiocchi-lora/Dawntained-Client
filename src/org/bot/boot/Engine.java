package org.bot.boot;

import javax.swing.JFrame;

import org.bot.loader.GameLoader;

public class Engine {

	private static Engine instance;
	private GameLoader gameLoader;
	private JFrame gameJFrame;
	private boolean debugMouse;

	private Engine() {
		gameLoader = new GameLoader();
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
