package org.ubot.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.ubot.bot.Bot;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.ui.serverselector.ServerSelectorScreen;
import org.ubot.util.directory.DirectoryManager;

public class Client extends Application {

	private static String username, accountKey, permissionKey;
	private Stage stage;
	private ClientModel model;

	public static void main(String[] args) {
		username = args[0];
		accountKey = args[1];
		if (args.length > 2) {
			permissionKey = args[2];
		}
		launch(args);
	}

	@Override
	public void init() throws Exception {
		if (permissionKey.equalsIgnoreCase("12345")) {
			DirectoryManager.init();
		}
		model = new ClientModel(Client.this, username, accountKey, permissionKey);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		stage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(final WindowEvent event) {
				terminate();
			}
		});
		displayScreen(new ServerSelectorScreen(this, model.getServerProviders()), "Server Selector");
		stage.show();
		System.out.println("Client launched.");
	}

	protected void displayScreen(final Scene scene, final String title) {
		stage.setTitle("[" + username + "] " + title);
		stage.setScene(scene);
		stage.sizeToScene();
	}

	public void resizeStage(double width, double height) {
		if (stage != null) {
			stage.setWidth(width);
			stage.setHeight(height);
		}
	}

	public void startBot(ServerLoader loader) {
		model.createBot(loader);
	}

	public void registerBot(Bot bot) {
		model.registerBot(bot);
	}

	public void close() {
		//stage.hide();
	}

	public void terminate() {
		Platform.exit();
	}

}
