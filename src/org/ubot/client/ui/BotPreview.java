package org.ubot.client.ui;

import org.ubot.bot.Bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BotPreview extends JButton {

	private final String name;
	private final Bot bot;

	public BotPreview(String name, Bot bot, ActionListener listener) {
		super("Bot");
		this.name = name;
		this.bot = bot;
		this.addActionListener(listener);
	}

	public String getName() {
		return name;
	}

	public Bot getBot() {
		return bot;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.drawImage(bot.getGameCanvas().getGameBuffer(), 0, 0, null);
	}
}
