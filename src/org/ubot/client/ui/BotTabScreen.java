package org.ubot.client.ui;

import org.ubot.client.Client;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BotTabScreen extends JTabbedPane implements ChangeListener {

	private final Client client;

	public BotTabScreen(Client client) {
		this.client = client;
		addTab();
		addChangeListener(this::stateChanged);
	}

	private final BotConfigurationScreen getNewConfigurationScreen() {
		return new BotConfigurationScreen(client, client.getServerProviders());
	}

	private void addTab() {
		addTab("Selector", getNewConfigurationScreen());
		addTab("+", getNewConfigurationScreen());
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		int selectedIndex = getSelectedIndex();
		if (selectedIndex == (getTabCount() - 1)) {
			System.out.println(selectedIndex);
		}
	}
}
