package org.ubot.client.ui.serverselector;

import org.ubot.client.Client;
import org.ubot.client.ui.serverselector.util.ExpandableListLabel;
import org.ubot.client.ui.serverselector.util.ExpandableTabListScreen;
import org.ubot.client.provider.ServerProvider;

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
		configure();
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
