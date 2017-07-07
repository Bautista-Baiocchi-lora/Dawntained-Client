package org.bot.ui;

import org.bot.Engine;
import org.bot.ui.screens.login.PortalScreen;
import org.bot.ui.screens.serverselector.ServerSelectorScreen;
import org.bot.util.directory.DirectoryManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BotUI extends Application {

	private InterfaceManager manager;
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	public BotUI() {
		Engine.getInstance().setDirectoryManager(new DirectoryManager());
		manager = new InterfaceManager(this);
	}

	protected void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("uBot v" + Engine.VERSION);
		stage.show();
		stage.setScene(new PortalScreen(manager));
		this.stage = stage;
	}

	protected void displayServerSelector() {
		displayScene(new ServerSelectorScreen(manager));
		stage.setWidth(500);
		stage.setHeight(300);
	}

	private void displayScene(final Scene scene) {
		if (stage != null) {
			stage.setScene(scene);
		}
	}

}
