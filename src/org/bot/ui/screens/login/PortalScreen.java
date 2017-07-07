package org.bot.ui.screens.login;

import org.bot.Engine;
import org.bot.ui.InterfaceManager;
import org.bot.ui.screens.Screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class PortalScreen extends Screen {

	private static VBox widgetStack;
	private TextField usernameField;
	private PasswordField passwordField;
	private CheckBox rememberCheck;
	private Label incorrectLogIn;

	public PortalScreen(InterfaceManager manager) {
		super(widgetStack = new VBox(), manager);
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
		usernameRow.getChildren().addAll(usernameLabel, usernameField);

		Label passwordLabel = new Label("Password:");
		passwordField = new PasswordField();
		passwordRow.getChildren().addAll(passwordLabel, passwordField);

		incorrectLogIn = new Label("Incorrect username and/or password!");
		incorrectLogIn.setTextFill(Color.RED);
		incorrectLogIn.setAlignment(Pos.CENTER);
		incorrectLogIn.setVisible(false);

		rememberCheck = new CheckBox("Remember Me");
		rememberCheck.setAlignment(Pos.CENTER);

		Button loginButton = new Button("Log In");
		loginButton.setMaxWidth(Double.MAX_VALUE);
		loginButton.setOnAction((e) -> {
			boolean rememberMe = rememberCheck.isSelected();
			if (!manager.logIn(usernameField.getText(), passwordField.getText(), rememberMe)) {
				incorrectLogIn();
			} else {
				manager.startServerSelector();
			}
		});

		Button asGuestButton = new Button("Continue As Guest");
		asGuestButton.setMaxWidth(Double.MAX_VALUE);
		asGuestButton.setOnAction((e) -> {
			Engine.getInstance().setUsername("Guest");
			manager.startServerSelector();
		});

		widgetStack.getChildren().addAll(incorrectLogIn, usernameRow, passwordRow, rememberCheck, loginButton,
				asGuestButton);
		widgetStack.setSpacing(10);
		widgetStack.setAlignment(Pos.CENTER);
	}

	private void incorrectLogIn() {
		incorrectLogIn.setVisible(true);
		usernameField.clear();
		passwordField.clear();
	}
}
