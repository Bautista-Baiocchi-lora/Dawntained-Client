package org.ubot.bot;

import javax.swing.*;

public class Bot extends JPanel {

	private static BotModel model;
	public Bot(BotModel.Builder builder) {
		model = builder.build();
	}

	public static String getBotName() {
		return model.getBotName();
	}


}
