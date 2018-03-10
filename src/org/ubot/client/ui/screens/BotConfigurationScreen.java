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
	private final JLabel title;
	private final JButton next;
	private JPanel infoPanel;


	public BotConfigurationScreen(Bot bot, ArrayList<ServerProvider> providers) {
		super(new BorderLayout());
		this.bot = bot;
		setBorder(BorderFactory.createLoweredBevelBorder());

		this.title = new JLabel("Server Providers", SwingConstants.CENTER);
		title.setFont(new Font("Courier New", Font.BOLD, 20));
		title.setBorder(BorderFactory.createEtchedBorder());
		this.add(title, BorderLayout.NORTH);

		this.providersListModel = new DefaultListModel<>();
		this.providerJList = new JList<>(providersListModel);
		populateProvidersModel(providers);
		providerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		providerJList.addListSelectionListener(e -> displayServerInfo(providerJList.getSelectedValue()));
		providerJList.setBorder(BorderFactory.createEtchedBorder());
		this.add(providerJList, BorderLayout.CENTER);

		this.accountListModel = new DefaultListModel<>();
		this.accountJList = new JList<>(accountListModel);
		accountJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountJList.addListSelectionListener(e -> displayAccountInfo(accountJList.getSelectedValue()));
		accountJList.setBorder(BorderFactory.createEtchedBorder());

		this.next = new JButton("Next");
		this.next.setActionCommand("next");
		this.next.addActionListener(this);
		this.add(next, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(765, 503));
	}

	private void displayServerInfo(ServerProvider provider) {
		Box layout = Box.createVerticalBox();
		layout.add(new JLabel("Name: " + provider.getManifest().serverName()));
		layout.add(new JLabel("Author: " + provider.getManifest().author()));
		layout.add(new JLabel("Version: " + provider.getManifest().version()));
		layout.add(new JLabel("Information: " + provider.getManifest().info()));
		add(infoPanel = generateInfoPanel(layout), BorderLayout.EAST);
		revalidate();
	}

	private void displayAccountInfo(Account account) {
		Box layout = Box.createVerticalBox();
		layout.add(new JLabel("Name: " + account.getUsername()));
		layout.add(new JLabel("Password: " + account.getPassword()));
		layout.add(new JLabel("Server: " + account.getServer()));
		layout.add(new JLabel("Sleep Duration: " + account.getSleepDuration()));
		layout.add(new JLabel("Sleep Interval: " + account.getSleepInterval()));
		add(infoPanel = generateInfoPanel(layout), BorderLayout.EAST);
		revalidate();
	}

	private final JPanel generateInfoPanel(Box layout) {
		final JPanel serverInfoPanel = new JPanel();
		serverInfoPanel.add(layout);
		serverInfoPanel.setBorder(BorderFactory.createEtchedBorder());
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
			case "next":
				if (!providerJList.isSelectionEmpty()) {
					populateAccountsModel(null);
					remove(providerJList);
					remove(infoPanel);
					add(accountJList, BorderLayout.CENTER);
					title.setText("Accounts");
					next.setText("Start");
					next.setActionCommand("start");
					revalidate();
				}
				break;
			case "start":
				if (!accountJList.isSelectionEmpty()) {
					bot.setAccount(accountJList.getSelectedValue());
					bot.initiateServerLoader(providerJList.getSelectedValue());
				}
				break;
		}
	}
}
