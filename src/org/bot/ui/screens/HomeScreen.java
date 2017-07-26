package org.bot.ui.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class HomeScreen extends Scene implements Manageable {


	private static VBox layout;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();

	public HomeScreen() {
		super(layout = new VBox());
		configure();
	}

	private void configure() {
		layout.setStyle("-fx-padding: 10;");

		Button serverSelectorButton = new Button("Server Selector");
		serverSelectorButton.setMaxWidth(Double.MAX_VALUE);
		serverSelectorButton.setOnAction(event -> {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.SHOW_SERVER_SELECTOR).build());
		});

		Button accountManagerButton = new Button("Account Manager");
		accountManagerButton.setMaxWidth(Double.MAX_VALUE);
		accountManagerButton.setOnAction(event -> {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.SHOW_ACCOUNT_MANAGER).build());
		});

		layout.setSpacing(30);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(serverSelectorButton, accountManagerButton);
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
