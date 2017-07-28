package org.bot.ui.screens.serverselector;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.bot.provider.ServerProvider;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.screens.util.ExpandableTab;

public class ServerInformationTab extends ExpandableTab<ServerProvider> {

	public ServerInformationTab(ServerProvider provider) {
		super(provider);
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
		Label typeLabel = new Label("Type: " + object.getManifest().type().getSimpleName());
		Label revisionLabel = new Label("Revision: " + object.getManifest().revision().toString());
		Label infoLabel = new Label("Info");
		TextArea infoArea = new TextArea(object.getManifest().info());
		infoArea.setWrapText(true);
		infoArea.setEditable(false);
		infoArea.setMaxWidth(250);

		Button launchButton = new Button("Launch");
		launchButton.setOnAction((e) -> {
			requestAction(
					new InterfaceActionRequest.ActionBuilder(InterfaceAction.LOAD_SERVER).provider(object).build());
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


}
