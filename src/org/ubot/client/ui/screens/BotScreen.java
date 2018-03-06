package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;

import javax.swing.*;
import java.awt.*;

public class BotScreen extends JPanel {

	private final Bot bot;

	public BotScreen(Bot bot) {
		super(new BorderLayout());
		this.bot = bot;
		setBorder(BorderFactory.createLoweredBevelBorder());
		add(configureApplet(bot), BorderLayout.CENTER);
	}

	private final Component configureApplet(Bot bot) {
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
