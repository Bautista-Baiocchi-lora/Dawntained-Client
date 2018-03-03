package org.ubot.client.ui.serverselector.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ubot.client.Client;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public abstract class ExpandableTabListScreen<T extends Object> extends Scene {

	private final Client client;
	protected static VBox layout;
	private static HBox mainLayout;
	private ExpandableTab informationTab;

	public ExpandableTabListScreen(Client client) {
		super(mainLayout = new HBox());
		this.client = client;
		mainLayout.setStyle("-fx-padding: 10;");
		mainLayout.setSpacing(5);
	}

	protected void configure() {
		layout = new VBox();

		ListView<ExpandableListLabel<T>> list = new ListView<ExpandableListLabel<T>>();
		list.setEditable(false);
		ObservableList<ExpandableListLabel<T>> items = FXCollections.observableArrayList(getListLabels());
		list.setItems(items);
		list.setOnMouseClicked((e) -> {
			if (list.getSelectionModel().getSelectedItem() != null) {
				displayTab(list.getSelectionModel().getSelectedItem().getTab());
			}
		});
		list.setMaxWidth(250);

		layout.setSpacing(10);
		layout.getChildren().add(list);

		mainLayout.getChildren().add(layout);
	}

	protected abstract ArrayList<ExpandableListLabel<T>> getListLabels();

	private void displayTab(ExpandableTab tab) {
		client.resizeStage(500, 300);
		if (informationTab == null) {
			mainLayout.getChildren().add(informationTab = tab);
		} else {
			mainLayout.getChildren().remove(informationTab);
			mainLayout.getChildren().add(informationTab = tab);
		}
	}

}