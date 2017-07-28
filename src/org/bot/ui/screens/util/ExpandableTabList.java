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
public abstract class ExpandableTabList extends Scene implements Manageable {

	protected static HBox layout;
	private ExpandableInformationTab informationTab;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();

	public ExpandableTabList() {
		super(layout = new HBox());
		configure();
	}

	private void configure() {
		VBox componentLayout = new VBox();

		ListView<ExpandableListLabel> list = new ListView<ExpandableListLabel>();
		list.setEditable(false);
		ObservableList<ExpandableListLabel> items = FXCollections.observableArrayList(getListLabels());
		list.setItems(items);
		list.setOnMouseClicked((e) -> {
			if (list.getSelectionModel().getSelectedItem() != null) {
				displayTab(list.getSelectionModel().getSelectedItem().getInformationTab());
			}
		});
		list.setMaxWidth(250);

		componentLayout.setSpacing(15);
		componentLayout.getChildren().add(list);
		layout.getChildren().add(componentLayout);
	}

	protected abstract ExpandableListLabel getListLabels();

	private void displayTab(ExpandableInformationTab tab) {
		if (this.getWindow().getWidth() == 250) {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.RESIZE_STAGE).size(500, 300).build());
		}
		if (informationTab == null) {
			layout.getChildren().add(informationTab = tab);
		} else {
			layout.getChildren().remove(informationTab);
			layout.getChildren().add(informationTab = tab);
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
