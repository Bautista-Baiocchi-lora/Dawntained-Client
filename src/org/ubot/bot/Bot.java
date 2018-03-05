package org.ubot.bot;

import javax.swing.*;
import java.awt.*;

public class Bot extends JPanel {

	private static BotModel model;

	public Bot(BotModel.Builder builder) {
		model = builder.build();
		setPreferredSize(new Dimension(765, 503));
		setLayout(new BorderLayout());
		if (model.getApplet() != null) {
			add(model.getApplet(), BorderLayout.CENTER);
			revalidate();
			model.getApplet().init();
			if (!model.getApplet().isActive()) {
				model.getApplet().start();
			}
		} else {
			add(new JLabel("Error: Corrupted Applet"));
		}
	}

	public static String getBotName() {
		return model.getBotName();
	}


}
