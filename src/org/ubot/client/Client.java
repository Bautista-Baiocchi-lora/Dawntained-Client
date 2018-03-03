package org.ubot.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ubot.client.ui.serverselector.ServerSelectorScreen;
import org.ubot.util.directory.DirectoryManager;

public class Client extends Application {

	private static String username, accountKey;
	private Stage stage;
	private ClientModel model;

	public static void main(String[] args) {
		username = args[0];
		accountKey = args[1];
		launch(args);
	}

	@Override
	public void init() throws Exception {
		//DELETE IF NOT RUNNING IN IDE
		DirectoryManager.init();
		model = new ClientModel(Client.this, username, accountKey);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		displayScreen(new ServerSelectorScreen(this, model.getLocalServerProviders()), "Server Selector");
		stage.show();
		System.out.println("Client launched.");
	}

	protected void displayScreen(final Scene scene, final String title) {
		stage.setTitle("[" + username + "] " + title);
		stage.setScene(scene);
		stage.sizeToScene();
	}


}
