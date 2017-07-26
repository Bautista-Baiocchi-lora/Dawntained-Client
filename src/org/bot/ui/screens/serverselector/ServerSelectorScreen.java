package org.bot.ui.screens.serverselector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.bot.Engine;
import org.bot.provider.ServerProvider;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;
import java.util.Map;

public class ServerSelectorScreen extends Scene implements Manageable {


	private static HBox layout;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private ServerInformationTab serverTab;

	public ServerSelectorScreen() {
		super(layout = new HBox());
		configure();
	}

	private void configure() {
		ListView<ServerLabel> list = new ListView<ServerLabel>();
		list.setEditable(false);
		ObservableList<ServerLabel> items = FXCollections.observableArrayList(getServerLabels());
		list.setItems(items);
		list.setOnMouseClicked((e) -> {
			if (list.getSelectionModel().getSelectedItem() != null) {
				displayTab(list.getSelectionModel().getSelectedItem().getTab());
			}
		});
		list.setMaxWidth(250);
		layout.getChildren().addAll(list);
	}

	private ArrayList<ServerLabel> getServerLabels() {
		final ArrayList<ServerLabel> providers = new ArrayList<ServerLabel>();
		for (Map.Entry<String, ServerProvider> entry : Engine.getServerProviders().entrySet()) {
			providers.add(new ServerLabel(entry.getValue()));
		}
		return providers;
	}

	private void displayTab(ServerInformationTab tab) {
		if (this.getWindow().getWidth() == 250) {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.RESIZE_STAGE).size(500, 300).build());
		}
		if (serverTab == null) {
			serverTab = tab;
			layout.getChildren().add(serverTab);
		} else {
			layout.getChildren().remove(serverTab);
			serverTab = tab;
			layout.getChildren().add(serverTab);
		}
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
