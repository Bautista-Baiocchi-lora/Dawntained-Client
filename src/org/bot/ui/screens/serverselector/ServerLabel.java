package org.bot.ui.screens.serverselector;

import javafx.scene.control.Label;
import org.bot.provider.ServerProvider;
import org.bot.ui.BotUI;

public class ServerLabel extends Label {

	private final ServerInformationTab serverTab;

	public ServerLabel(ServerProvider provider) {
		super(provider.getManifest().serverName());
		serverTab = new ServerInformationTab(provider);
		serverTab.registerManager(BotUI.getInstance());
	}

	public ServerInformationTab getTab() {
		return serverTab;
	}

}
