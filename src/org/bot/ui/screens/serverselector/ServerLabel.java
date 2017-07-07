package org.bot.ui.screens.serverselector;

import org.bot.provider.ServerProvider;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.InterfaceManager;

import javafx.scene.control.Label;

public class ServerLabel extends Label {
	private final ServerInformationTab serverTab;

	public ServerLabel(ServerLoader<?> loader, ServerManifest manifest, InterfaceManager manager) {
		super(manifest.serverName());
		serverTab = new ServerInformationTab(new ServerProvider(loader, manifest), manager);
	}

	public ServerInformationTab getTab() {
		return serverTab;
	}
}
