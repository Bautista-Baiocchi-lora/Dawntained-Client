package org.ubot.client.ui;

import org.ubot.bot.Bot;
import org.ubot.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BotTheater extends JPanel implements ActionListener {

	private final Client client;
	private final JPanel theaterPanel;
	private final JButton addBot;

	public BotTheater(Client client) {
		super(new BorderLayout());
		this.client = client;
		this.theaterPanel = new JPanel(new GridLayout(0, 3));
		add(theaterPanel, BorderLayout.CENTER);
		this.addBot = new JButton("Add Bot");
		this.addBot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				client.showServerSelector();
			}
		});
		add(addBot, BorderLayout.SOUTH);
	}

	public final void displayPreviews(ArrayList<Bot> bots) {
		theaterPanel.removeAll();
		for (int index = 0; index < bots.size(); index++) {
			theaterPanel.add(getPreview(bots.get(index), index));
		}
		theaterPanel.revalidate();
	}

	private final BotPreview getPreview(Bot bot, int index) {
		return new BotPreview("Bot " + index, bot, this::actionPerformed);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final BotPreview source = (BotPreview) e.getSource();
		client.showBotScreen(source.getBot());
	}

}
