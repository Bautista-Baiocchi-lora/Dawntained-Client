package org.bot.ui.screens.serverselector;

import java.util.ArrayList;

import org.bot.provider.ServerProvider;
import org.bot.provider.manifest.ServerManifest;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ServerInformationTab extends ScrollPane implements Manageable {
	private final ServerProvider provider;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();

	public ServerInformationTab(ServerProvider provider) {
		setMaxWidth(250);
		this.provider = provider;
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
			requestAction(
					new InterfaceActionRequest.ActionBuilder(InterfaceAction.LOAD_SERVER).provider(provider).build());
		});

		GridPane grid = new GridPane();
		GridPane.setRowIndex(launchButton, 0);
		GridPane.setRowIndex(authorLabel, 1);
		GridPane.setRowIndex(versionLabel, 2);
		GridPane.setRowIndex(revisionLabel, 3);
		GridPane.setRowIndex(typeLabel, 4);
		GridPane.setRowIndex(infoLabel, 5);
		GridPane.setRowIndex(infoArea, 6);
		grid.getChildren().addAll(launchButton, authorLabel, versionLabel, revisionLabel, typeLabel, infoLabel,
				infoArea);

		layout.getChildren().addAll(grid);
		setContent(layout);
	}

	@Override
	public void requestAction(InterfaceActionRequest action) {
		for (Manager manager : managers) {
			manager.processActionRequest(action);
		}
	}

	@Override
	public void registerManager(Manager manager) {
		managers.add(manager);
	}
}
