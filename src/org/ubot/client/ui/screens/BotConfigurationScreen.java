package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;
import org.ubot.client.provider.ServerProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BotConfigurationScreen extends JPanel implements ActionListener {

	private final Bot bot;
	private final DefaultListModel<ServerProvider> providersListModel;
	private final JList<ServerProvider> providerJList;
	private final JButton start;

	public BotConfigurationScreen(Bot bot, ArrayList<ServerProvider> providers) {
		super(new BorderLayout());
		this.bot = bot;
		this.providersListModel = new DefaultListModel<>();
		setBorder(BorderFactory.createLoweredBevelBorder());
		populateProvidersModel(providers);
		providerJList = new JList<>(providersListModel);
		providerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(providerJList, BorderLayout.CENTER);
		this.start = new JButton("Start Bot");
		this.start.setActionCommand("start");
		this.start.addActionListener(this);
		this.add(start, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(765, 503));
	}

	private final void populateProvidersModel(ArrayList<ServerProvider> providers) {
		for (ServerProvider provider : providers) {
			this.providersListModel.addElement(provider);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "start":
				bot.initiateServerLoader(providerJList.getSelectedValue().getLoader());
				break;
		}
	}
}
