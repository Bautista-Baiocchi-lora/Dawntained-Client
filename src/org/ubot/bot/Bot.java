package org.ubot.bot;

import org.ubot.component.RSCanvas;

import java.applet.Applet;

public class Bot {

	private static BotModel model;

	public Bot(BotModel model) {
		this.model = model;
	}

	public static String getBotName() {
		return model.getBotName();
	}

	public RSCanvas getGameCanvas() {
		return model.getCanvas();
	}

	public Applet getApplet() {
		return model.getApplet();
	}

}
