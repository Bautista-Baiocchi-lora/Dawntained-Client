package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;
import org.ubot.client.account.Account;
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
	private final DefaultListModel<Account> accountListModel;
	private final JList<Account> accountJList;
	private final JButton start;

	public BotConfigurationScreen(Bot bot, ArrayList<ServerProvider> providers) {
		super(new BorderLayout());
		this.bot = bot;
		setBorder(BorderFactory.createLoweredBevelBorder());

		this.providersListModel = new DefaultListModel<>();
		this.providerJList = new JList<>(providersListModel);
		populateProvidersModel(providers);
		providerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		providerJList.addListSelectionListener(e -> displayServerInfo(providerJList.getSelectedValue()));
		this.add(providerJList, BorderLayout.WEST);

		this.accountListModel = new DefaultListModel<>();
		this.accountJList = new JList<>(accountListModel);
		populateAccountsModel(null);
		accountJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountJList.addListSelectionListener(e -> displayAccountInfo(accountJList.getSelectedValue()));
		this.add(accountJList, BorderLayout.EAST);

		this.start = new JButton("Start Bot");
		this.start.setActionCommand("start");
		this.start.addActionListener(this);
		this.add(start, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(765, 503));
	}

	private void displayServerInfo(ServerProvider provider) {
		Box layout = Box.createVerticalBox();
		layout.add(new JLabel("Name: " + provider.getManifest().serverName()));
		layout.add(new JLabel("Author: " + provider.getManifest().author()));
		layout.add(new JLabel("Version: " + provider.getManifest().version()));
		layout.add(new JLabel("Information: " + provider.getManifest().info()));
		add(generateInfoPanel(layout), BorderLayout.CENTER);
		revalidate();
	}

	private void displayAccountInfo(Account account) {
		Box layout = Box.createVerticalBox();
		layout.add(new JLabel("Name: " + account.getUsername()));
		layout.add(new JLabel("Password: " + account.getPassword()));
		layout.add(new JLabel("Server: " + account.getServer()));
		layout.add(new JLabel("Sleep Duration: " + account.getSleepDuration()));
		layout.add(new JLabel("Sleep Interval: " + account.getSleepInterval()));
		add(generateInfoPanel(layout));
		revalidate();
	}

	private final JPanel generateInfoPanel(Box layout) {
		final JPanel serverInfoPanel = new JPanel();
		serverInfoPanel.add(layout);
		return serverInfoPanel;
	}

	private final void populateAccountsModel(ArrayList<Account> accounts) {
		this.accountListModel.addElement(new Account("Default", "All"));
		if (accounts == null) {
			return;
		}
		for (Account account : accounts) {
			this.accountListModel.addElement(account);
		}
	}

	private final void populateProvidersModel(ArrayList<ServerProvider> providers) {
		if (providers == null) {
			return;
		}
		for (ServerProvider provider : providers) {
			this.providersListModel.addElement(provider);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (e.getActionCommand()) {
			case "start":
				bot.setAccount(accountJList.getSelectedValue());
				bot.initiateServerLoader(providerJList.getSelectedValue());
				break;
		}
	}
}
