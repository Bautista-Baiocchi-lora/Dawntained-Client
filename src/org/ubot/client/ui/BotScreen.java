package org.ubot.client.ui;

import org.ubot.bot.BotModel;
import org.ubot.client.Client;

import javax.swing.*;
import java.awt.*;

public class BotScreen extends JPanel {

	private final Client client;

	public BotScreen(Client client, BotModel model) {
		this.client = client;
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
}
