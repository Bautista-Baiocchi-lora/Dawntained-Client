package org.ubot.launcher;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PortalScreen extends Scene {

	private static VBox widgetStack;
	private TextField usernameField;
	private PasswordField passwordField;
	private CheckBox rememberCheck;
	private Label incorrectLogIn;

	public PortalScreen() {
		super(widgetStack = new VBox());
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
		usernameField.setText("bautista");
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
		loginButton.setDefaultButton(true);
		loginButton.setMaxWidth(Double.MAX_VALUE);
		loginButton.setOnAction((e) -> {
			if (!logIn(usernameField.getText(), passwordField.getText(), rememberCheck.isSelected())) {
				incorrectLogIn();
			} else {
				terminate();
			}
		});

		Button asGuestButton = new Button("Continue As Guest");
		asGuestButton.setMaxWidth(Double.MAX_VALUE);
		asGuestButton.setOnAction((e) -> {
			if (launchClient("guest", "")) {
				terminate();
			}
		});

		widgetStack.setSpacing(10);
		widgetStack.setAlignment(Pos.CENTER);
		widgetStack.getChildren().addAll(incorrectLogIn, usernameRow, passwordRow, rememberCheck, loginButton,
				asGuestButton);
	}

	public void terminate() {
		Platform.exit();
	}

	private boolean logIn(String username, String password, boolean rememberMe) {
		if ((username.equalsIgnoreCase("bautista") || username.equalsIgnoreCase("ethan") || username.equalsIgnoreCase("")) && password.equals("")) {
			if (rememberMe) {
			}
			return launchClient(username, "ACC-KEY");
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

	private boolean launchClient(String username, String key) {
		final ProcessBuilder builder = new ProcessBuilder().command("java").command("org.ubot.client.Client").command(username).command(key);
		final Process process;
		try {
			process = builder.start();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
