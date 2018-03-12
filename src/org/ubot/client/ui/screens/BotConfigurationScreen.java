package org.ubot.client.ui.screens;

import org.ubot.bot.Bot;
import org.ubot.client.account.Account;
import org.ubot.client.account.AccountManager;
import org.ubot.client.provider.ServerProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BotConfigurationScreen extends JPanel implements ActionListener {

	private final Bot bot;
	private final AccountManager accountManager;
	private final DefaultListModel<ServerProvider> providersListModel;
	private final JList<ServerProvider> providerJList;
	private final DefaultListModel<Account> accountListModel;
	private final JList<Account> accountJList;
	private final JLabel title;
	private final JButton nextStep;
	private JPanel infoPanel;


	public BotConfigurationScreen(Bot bot, AccountManager accountManager, ArrayList<ServerProvider> providers) {
		super(new BorderLayout());
		this.bot = bot;
		this.accountManager = accountManager;
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
		populateAccountsModel(accountManager.getAccounts());
		accountJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountJList.addListSelectionListener(e -> displayAccountInfo(accountJList.getSelectedValue()));
		accountJList.setBorder(BorderFactory.createEtchedBorder());

		this.nextStep = new JButton("Next");
		this.nextStep.setActionCommand("nextStep");
		this.nextStep.addActionListener(this);
		this.add(nextStep, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(765, 503));
	}

	private void displayServerInfo(ServerProvider provider) {
		final JPanel serverInfoPanel = new JPanel();
		final Box layout = Box.createVerticalBox();
		layout.add(new JLabel("Name: " + provider.getManifest().serverName()));
		layout.add(new JLabel("Author: " + provider.getManifest().author()));
		layout.add(new JLabel("Version: " + provider.getManifest().version()));
		layout.add(new JLabel("Information: " + provider.getManifest().info()));
		serverInfoPanel.add(layout);
		serverInfoPanel.setBorder(BorderFactory.createEtchedBorder());
		displayInfoPanel(serverInfoPanel);
	}

	private void displayAccountInfo(Account account) {
		final JPanel accountInfoPanel = new JPanel(new GridLayout(0, 2));
		accountInfoPanel.setBorder(BorderFactory.createEtchedBorder());

		accountInfoPanel.add(new JLabel("Name: "));
		final JTextField usernameField = new JTextField(account.getUsername());
		accountInfoPanel.add(usernameField);

		accountInfoPanel.add(new JLabel("Password: "));
		final JPasswordField passwordField = new JPasswordField(account.getPassword());
		accountInfoPanel.add(passwordField);

		accountInfoPanel.add(new JLabel("Servers: "));
		final JTextField serverField = new JTextField(account.getServer());
		accountInfoPanel.add(serverField);

		accountInfoPanel.add(new JLabel("Sleeping: "));
		final JCheckBox breaking = new JCheckBox();
		breaking.setSelected(account.isBreaking());
		accountInfoPanel.add(breaking);

		accountInfoPanel.add(new JLabel("Sleep Duration (Minutes): "));
		final JSpinner duration = new JSpinner();
		duration.setValue(account.getSleepDuration());
		accountInfoPanel.add(duration);

		accountInfoPanel.add(new JLabel("Sleep Intervals (Minutes): "));
		final JSpinner interval = new JSpinner();
		interval.setValue(account.getSleepInterval());
		accountInfoPanel.add(interval);

		final JButton saveChanges = new JButton("Save Changes");
		saveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				account.setUsername(usernameField.getText());
				account.setPassword(String.valueOf(passwordField.getPassword()));
				account.setServer(serverField.getText());
				account.setBreaking(breaking.isSelected());
				account.setSleepDuration((int) duration.getValue());
				account.setSleepInterval((int) duration.getValue());
				accountManager.saveAccounts();
			}
		});
		accountInfoPanel.add(saveChanges);

		displayInfoPanel(accountInfoPanel);
	}

	private void displayInfoPanel(JPanel panel) {
		if (infoPanel != null) {
			remove(infoPanel);
		}
		add(infoPanel = panel, BorderLayout.EAST);
		revalidate();
	}

	private final void populateAccountsModel(ArrayList<Account> accounts) {
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
			case "nextStep":
				if (!providerJList.isSelectionEmpty()) {
					remove(providerJList);
					remove(infoPanel);
					add(accountJList, BorderLayout.CENTER);
					title.setText("Accounts");
					nextStep.setText("Start");
					nextStep.setActionCommand("start");
					revalidate();
				}
				break;
			case "start":
				if (!accountJList.isSelectionEmpty()) {
					bot.setAccount(accountJList.getSelectedValue());
					bot.initiateServerLoader(providerJList.getSelectedValue());
				}
				break;
			case "delete":
				break;
			case "add":
				break;
		}
	}
}
