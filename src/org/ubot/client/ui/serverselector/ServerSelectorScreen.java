package org.ubot.client.ui.serverselector;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.ubot.client.Client;
import org.ubot.client.ui.serverselector.util.ExpandableListLabel;
import org.ubot.client.ui.serverselector.util.ExpandableTabListScreen;
import org.ubot.client.provider.ServerProvider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerSelectorScreen extends ExpandableTabListScreen<ServerProvider>  {

	private final Client client;
	private final HashMap<String, ServerProvider> serverProviders;

	public ServerSelectorScreen(Client client, HashMap<String, ServerProvider> providers) {
		super(client);
		this.serverProviders = providers;
		this.client = client;
		configure();
		final Button closeButton = new Button("Close");
		closeButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
			@Override
			public void handle(final javafx.event.ActionEvent event) {
				client.terminate();
			}
		});
		this.mainLayout.getChildren().add(closeButton);
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
