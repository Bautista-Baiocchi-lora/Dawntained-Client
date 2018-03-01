package org.ubot.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ubot.bot.BotModel;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.ui.screens.serverselector.ServerSelectorScreen;

import javax.swing.*;

public class Client extends Application {

	private final ClientModel model;
	private Stage stage;

	public Client(String username, String accountKey) {
		//TODO: check user permissions
		this.model = new ClientModel(this, username, accountKey, true);
	}

	public static void main(String[] args) {
		new Client(args[0], args[1]);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(args);
	}


	public void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		displayScreen(new ServerSelectorScreen(Client.this, model.getServerProviders()), "Server Selector");
		stage.show();
		Logger.log("uBot Client launched.", LogType.CLIENT);
	}

	protected void displayScreen(final Scene scene, final String title) {
		stage.setTitle(title);
		stage.setScene(scene);
		stage.sizeToScene();
		Logger.log("Scene changed.", LogType.DEBUG);
	}

	public void resizeStage(double width, double height) {
		if (stage != null) {
			stage.setWidth(width);
			stage.setHeight(height);
			Logger.log("Resize stage to: " + width + ", " + height, LogType.DEBUG);
		}
	}

	public void openBot(BotModel.Builder modelBuilder) {
		model.registerBot(modelBuilder);
		//terminate();
	}

	public void launchServerProvider(ServerProvider provider) {
		model.launchServer(provider);
	}

}
