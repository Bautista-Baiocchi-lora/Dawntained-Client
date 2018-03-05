package org.ubot.client.ui;

import org.ubot.bot.Bot;
import org.ubot.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotScreen extends JPanel implements ActionListener {

	private final Client client;
	private final BotToolBar toolBar;

	public BotScreen(Client client, Bot bot) {
		super(new BorderLayout());
		this.client = client;
		this.toolBar = new BotToolBar(client);
		add(bot.getApplet());
		add(toolBar, BorderLayout.NORTH);
		client.setResizable(true);
	}

	private final JPanel generateSettingsPanel() {
		final JPanel settingsPanel = new JPanel();
		//TODO:Settings
		settingsPanel.add(new JLabel("This a setting"));
		return settingsPanel;
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

	@Override
	public void actionPerformed(final ActionEvent e) {
		client.showBotTheater();
	}
}
