package org.baiocchi.client;

import java.lang.reflect.InvocationTargetException;

import org.baiocchi.client.reflection.Game;
import org.baiocchi.client.ui.BotWindow;
import org.baiocchi.client.util.Cache;

public class Engine {

	private final BotWindow window;
	private final Game game;
	private static Engine instance;

	private Engine() {
		Cache.revalidate();
		game = new Game();
		window = new BotWindow();
	}

	public void start() {
		try {
			window.addApplet(game.getApplet());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		window.setVisible(true);
	}

	public Game getGame() {
		return game;
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}
