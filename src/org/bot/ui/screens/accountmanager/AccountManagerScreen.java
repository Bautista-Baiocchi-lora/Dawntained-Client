package org.bot.ui.screens.accountmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bot.Engine;
import org.bot.account.Account;
import org.bot.account.AccountManager;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;

import java.util.ArrayList;

/**
 * Created by bautistabaiocchi-lora on 7/23/17.
 */
public class AccountManagerScreen extends Scene implements Manageable {

	private static HBox layout;
	private final AccountManager manager;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private AccountInformationTab accountTab;

	public AccountManagerScreen() {
		super(layout = new HBox());
		Engine.setAccountManager(manager = new AccountManager());
		layout.setStyle("-fx-padding: 10;");
		configure();
	}

	private void configure() {
		VBox componentLayout = new VBox();

		ListView<AccountLabel> list = new ListView<AccountLabel>();
		list.setEditable(false);
		ObservableList<AccountLabel> items = FXCollections.observableArrayList(getAccountLabels());
		list.setItems(items);
		list.setOnMouseClicked((e) -> {
			if (list.getSelectionModel().getSelectedItem() != null) {
				displayTab(list.getSelectionModel().getSelectedItem().getInformationTab());
			}
		});
		list.setMaxWidth(250);

		Button addButton = new Button("Add Account");
		addButton.setMaxWidth(Double.MAX_VALUE);
		addButton.setOnAction((e) -> {
			//add account
		});

		componentLayout.setSpacing(15);
		componentLayout.getChildren().addAll(list, addButton);

		layout.getChildren().addAll(componentLayout);
	}

	private ArrayList<AccountLabel> getAccountLabels() {
		ArrayList<AccountLabel> accountLabels = new ArrayList<AccountLabel>();
		for (Account account : manager.getAccounts()) {
			accountLabels.add(new AccountLabel(account));
		}
		return accountLabels;
	}

	private void displayTab(AccountInformationTab tab) {
		if (this.getWindow().getWidth() == 250) {
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.RESIZE_STAGE).size(500, 300).build());
		}
		if (accountTab == null) {
			accountTab = tab;
			layout.getChildren().add(accountTab);
		} else {
			layout.getChildren().remove(accountTab);
			accountTab = tab;
			layout.getChildren().add(accountTab);
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
