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
		this.add(providerJList, BorderLayout.WEST);

		this.accountListModel = new DefaultListModel<>();
		this.accountJList = new JList<>(accountListModel);
		populateAccountsModel(null);
		accountJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(accountJList, BorderLayout.EAST);

		this.start = new JButton("Start Bot");
		this.start.setActionCommand("start");
		this.start.addActionListener(this);
		this.add(start, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(765, 503));
	}

	public final void populateAccountsModel(ArrayList<Account> accounts) {
		this.accountListModel.addElement(new Account("Default", "All"));
		this.accountJList.setSelectedIndex(0);
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
		if (!providersListModel.isEmpty()) {
			providerJList.setSelectedIndex(0);
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
