package org.bot.ui.screens.login;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.bot.Engine;
import org.bot.ui.management.InterfaceAction;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;
import org.bot.util.ConfigManager;
import org.bot.util.Utilities;

import java.util.ArrayList;

public class PortalScreen extends Scene implements Manageable {

	private static VBox widgetStack;
	private final ArrayList<Manager> managers = new ArrayList<Manager>();
	private final ConfigManager configManager = Engine.getConfigManager();
	private final boolean rememberMe;
	private TextField usernameField;
	private PasswordField passwordField;
	private CheckBox rememberCheck;
	private Label incorrectLogIn;

	public PortalScreen() {
		super(widgetStack = new VBox());
		rememberMe = Boolean.parseBoolean(configManager.getProperty("rememberme"));
		configure();
	}

	private void configure() {
		HBox usernameRow = new HBox();
		usernameRow.setSpacing(5);
		usernameRow.setAlignment(Pos.CENTER);

		HBox passwordRow = new HBox();
		passwordRow.setSpacing(5);
		passwordRow.setAlignment(Pos.CENTER);

		widgetStack.setStyle("-fx-padding: 10;");

		Label usernameLabel = new Label("Username:");
		usernameField = new TextField();
		usernameField.setText(rememberMe ? configManager.getProperty("username") : "");
		usernameRow.getChildren().addAll(usernameLabel, usernameField);

		Label passwordLabel = new Label("Password:");
		passwordField = new PasswordField();
		passwordRow.getChildren().addAll(passwordLabel, passwordField);

		incorrectLogIn = new Label("Incorrect username and/or password!");
		incorrectLogIn.setTextFill(Color.RED);
		incorrectLogIn.setAlignment(Pos.CENTER);
		incorrectLogIn.setVisible(false);

		rememberCheck = new CheckBox("Remember Me");
		rememberCheck.setSelected(rememberMe);
		rememberCheck.setAlignment(Pos.CENTER);

		Button loginButton = new Button("Log In");
		loginButton.setDefaultButton(true);
		loginButton.setMaxWidth(Double.MAX_VALUE);
		loginButton.setOnAction((e) -> {
			if (!logIn(usernameField.getText(), passwordField.getText(), rememberCheck.isSelected())) {
				incorrectLogIn();
			}
		});

		Button asGuestButton = new Button("Continue As Guest");
		asGuestButton.setMaxWidth(Double.MAX_VALUE);
		asGuestButton.setOnAction((e) -> {
			Engine.setUsername("Guest");
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.SHOW_SERVER_SELECTOR).build());
		});

		widgetStack.setSpacing(10);
		widgetStack.setAlignment(Pos.CENTER);
		widgetStack.getChildren().addAll(incorrectLogIn, usernameRow, passwordRow, rememberCheck, loginButton,
				asGuestButton);

		if (rememberMe) {
			passwordField.requestFocus();
		}
	}

	private boolean logIn(String username, String password, boolean rememberMe) {
		if ((username.equalsIgnoreCase("bautista") && password.equals("123")) || (username.equalsIgnoreCase("ethan") && password.equals("123")) || (username.equalsIgnoreCase("") && password.equals(""))) {
			if (rememberMe) {
				configManager.saveProperty("rememberme", Boolean.toString(rememberMe));
				configManager.saveProperty("username", username);
			}
			Engine.setUsername(Utilities.capitalize(username));
			Engine.setDeveloper(true);
			requestAction(new InterfaceActionRequest.ActionBuilder(InterfaceAction.SHOW_SERVER_SELECTOR).build());
			return true;
		}
		/**
		 * HANDLE WEB-BASED LOGIN HERE.
		 */
		return false;
	}

	private void incorrectLogIn() {
		incorrectLogIn.setVisible(true);
		usernameField.clear();
		passwordField.clear();
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
