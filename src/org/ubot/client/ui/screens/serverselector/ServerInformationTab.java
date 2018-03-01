package org.ubot.client.ui.screens.serverselector;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.ubot.client.Client;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.ui.screens.util.ExpandableTab;

public class ServerInformationTab extends ExpandableTab<ServerProvider> {

	private final Client client;

	public ServerInformationTab(Client client, ServerProvider provider) {
		super(provider);
		this.client = client;
	}

	@Override
	protected String getLabelTitle() {
		return object.getManifest().serverName();
	}

	@Override
	protected void configure() {
		VBox layout = new VBox();

		Label authorLabel = new Label("Author: " + object.getManifest().author());
		Label versionLabel = new Label("Version: " + String.valueOf(object.getManifest().version()));
		Label revisionLabel = new Label("Revision: " + object.getManifest().revision().toString());
		Label infoLabel = new Label("Info");
		TextArea infoArea = new TextArea(object.getManifest().info());
		infoArea.setWrapText(true);
		infoArea.setEditable(false);
		infoArea.setMaxWidth(250);

		Button launchButton = new Button("Launch");
		launchButton.setOnAction((e) -> {
			client.launchServerProvider(object);
		});

		GridPane grid = new GridPane();
		GridPane.setRowIndex(launchButton, 0);
		GridPane.setRowIndex(authorLabel, 1);
		GridPane.setRowIndex(versionLabel, 2);
		GridPane.setRowIndex(revisionLabel, 3);
		GridPane.setRowIndex(infoLabel, 4);
		GridPane.setRowIndex(infoArea, 5);
		grid.getChildren().addAll(launchButton, authorLabel, versionLabel, revisionLabel, infoLabel,
				infoArea);

		layout.getChildren().addAll(grid);
		setContent(layout);
	}


}
