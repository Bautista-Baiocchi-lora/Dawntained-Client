package org.bot.ui.screens.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/26/17.
 */
public abstract class ExpandableTabListScreen<T extends Object> extends Scene implements Manageable {

	protected static VBox layout;
	private static HBox mainLayout;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private ExpandableTab informationTab;

	public ExpandableTabListScreen() {
		super(mainLayout = new HBox());
		mainLayout.setStyle("-fx-padding: 10;");
		mainLayout.setSpacing(5);
		configure();
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
		if (this.getWindow().getWidth() == 250) {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.RESIZE_STAGE).size(500, 300).build());
		}
		if (informationTab == null) {
			mainLayout.getChildren().add(informationTab = tab);
		} else {
			mainLayout.getChildren().remove(informationTab);
			mainLayout.getChildren().add(informationTab = tab);
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
