package org.bot.ui.screens.accountmanager;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bot.account.Account;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountInformationTab extends ScrollPane implements Manageable {


	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private final Account account;

	public AccountInformationTab(Account account) {
		this.account = account;
		configure();
	}

	private void configure() {
		VBox layout = new VBox();

		Label usernameLabel = new Label("Username");
		TextField usernameField = new TextField(account.getUsername());
		Label passwordLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();
		passwordField.setText(account.getPassword());

		Label serversLabel = new Label("Servers");

		ListView<String> list = new ListView<String>();
		HBox listButtons = new HBox();

		layout.setSpacing(10);
		layout.setStyle("-fx-padding: 10;");
		layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
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
