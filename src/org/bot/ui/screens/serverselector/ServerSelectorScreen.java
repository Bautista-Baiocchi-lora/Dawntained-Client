package org.bot.ui.screens.serverselector;

import org.bot.Engine;
import org.bot.provider.ServerProvider;
import org.bot.ui.screens.util.ExpandableListLabel;
import org.bot.ui.screens.util.ExpandableTabListScreen;

import java.util.ArrayList;
import java.util.Map;

public class ServerSelectorScreen extends ExpandableTabListScreen<ServerProvider> {


	public ServerSelectorScreen() {
		super();
	}


	@Override
	protected ArrayList<ExpandableListLabel<ServerProvider>> getListLabels() {
		final ArrayList<ExpandableListLabel<ServerProvider>> providers = new ArrayList<ExpandableListLabel<ServerProvider>>();
		for (Map.Entry<String, ServerProvider> entry : Engine.getServerProviders().entrySet()) {
			providers.add(new ExpandableListLabel<ServerProvider>(new ServerInformationTab(entry.getValue())));
		}
		return providers;
	}


}
