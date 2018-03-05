package org.ubot.bot;

public class Bot {

	private static BotModel model;

	public Bot(BotModel model) {
		this.model = model;
	}

	public static String getBotName() {
		return model.getBotName();
	}

}
