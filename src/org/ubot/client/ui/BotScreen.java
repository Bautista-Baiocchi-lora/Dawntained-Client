package org.ubot.client.ui;

import org.ubot.bot.Bot;
import org.ubot.client.Client;

import javax.swing.*;
import java.awt.*;

public class BotScreen extends JPanel {

	private final Client client;

	public BotScreen(Client client, Bot bot) {
		super(new BorderLayout());
		this.client = client;
		add(generateBotComponent(bot), BorderLayout.CENTER);
	}

	private final Component generateBotComponent(Bot bot) {
		bot.getApplet().setPreferredSize(new Dimension(765, 503));
		if (bot.getApplet() != null) {
			bot.getApplet().init();
			if (!bot.getApplet().isActive()) {
				bot.getApplet().start();
			}
			bot.getApplet().revalidate();
			return bot.getApplet();
		}
		return new JLabel("Error: Corrupted Applet");
	}

}
