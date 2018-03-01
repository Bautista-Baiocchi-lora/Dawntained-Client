package org.ubot.client.ui.screens.serverselector;

import org.ubot.client.Client;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.ui.screens.util.ExpandableListLabel;
import org.ubot.client.ui.screens.util.ExpandableTabListScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerSelectorScreen extends ExpandableTabListScreen<ServerProvider> {

	private final Client client;
	private final HashMap<String, ServerProvider> serverProviders;

	public ServerSelectorScreen(Client client, HashMap<String, ServerProvider> providers) {
		super(client);
		this.serverProviders = providers;
		this.client = client;
	}


	@Override
	protected ArrayList<ExpandableListLabel<ServerProvider>> getListLabels() {
		final ArrayList<ExpandableListLabel<ServerProvider>> providers = new ArrayList<ExpandableListLabel<ServerProvider>>();
		for (Map.Entry<String, ServerProvider> entry : serverProviders.entrySet()) {
			providers.add(new ExpandableListLabel<>(new ServerInformationTab(client, entry.getValue())));

		}
		return providers;
	}

}
