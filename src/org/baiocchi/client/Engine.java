package org.baiocchi.client;

import java.net.MalformedURLException;

import javax.swing.JFrame;

import org.baiocchi.client.reflection.Game;
import org.baiocchi.client.util.Cache;

public class Engine {

	private final JFrame gameFrame;
	private Game game;
	private static Engine instance;

	private Engine() {
		Cache.revalidate();
		try {
			game = new Game();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		gameFrame = game.getGameFrame();
	}

	public void start() {
		gameFrame.setVisible(true);
	}

	public Game getGame() {
		return game;
	}

	public static Engine getInstance() {
		return instance == null ? instance = new Engine() : instance;
	}

}
