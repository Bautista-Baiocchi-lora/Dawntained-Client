package org.bot.ui.screens.serverselector;

import org.bot.provider.ServerProvider;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.InterfaceManager;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ServerInformationTab extends ScrollPane {
	private final InterfaceManager manager;
	private final ServerProvider provider;

	public ServerInformationTab(ServerProvider provider, InterfaceManager manager) {
		setMaxWidth(250);
		this.provider = provider;
		this.manager = manager;
		configure(provider.getManifest());
	}

	private void configure(ServerManifest manifest) {
		VBox layout = new VBox();

		Label authorLabel = new Label("Author: " + manifest.author());
		Label versionLabel = new Label("Version: " + String.valueOf(manifest.version()));
		Label typeLabel = new Label("Type: " + manifest.type().getSimpleName());
		Label revisionLabel = new Label("Revision: " + manifest.revision().toString());
		Label infoLabel = new Label("Info");
		TextArea infoArea = new TextArea(manifest.info());
		infoArea.setWrapText(true);
		infoArea.setEditable(false);
		infoArea.setMaxWidth(250);

		Button launchButton = new Button("Launch");
		launchButton.setOnAction((e) -> {
			manager.loadServer(provider);
		});

		GridPane grid = new GridPane();
		GridPane.setRowIndex(authorLabel, 0);
		GridPane.setRowIndex(versionLabel, 1);
		GridPane.setRowIndex(revisionLabel, 2);
		GridPane.setRowIndex(typeLabel, 3);
		GridPane.setRowIndex(infoLabel, 4);
		GridPane.setRowIndex(infoArea, 5);
		GridPane.setRowIndex(launchButton, 6);
		grid.getChildren().addAll(authorLabel, versionLabel, revisionLabel, typeLabel, infoLabel, infoArea,
				launchButton);

		layout.getChildren().addAll(grid);
		setContent(layout);
	}
}
