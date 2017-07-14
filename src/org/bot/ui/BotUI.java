package org.bot.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bot.Engine;
import org.bot.provider.ServerProvider;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manager;
import org.bot.ui.screens.loading.LoadingScreen;
import org.bot.ui.screens.login.PortalScreen;
import org.bot.ui.screens.serverselector.ServerSelectorScreen;
import org.bot.util.directory.DirectoryManager;

public class BotUI extends Application implements Manager {

	public static Stage stage;
	private static BotUI instance = new BotUI();

	public BotUI() {
		Engine.setDirectoryManager(new DirectoryManager());
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static BotUI getInstance() {
		return instance;
	}

	private void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		PortalScreen screen = new PortalScreen();
		screen.registerManager(this);
		displayScreen(screen);
		stage.show();
	}

	private void displayScreen(final Scene scene) {
		stage.setTitle(Engine.getInterfaceTitle());
		stage.setScene(scene);
		stage.sizeToScene();
	}

	@Override
	public void processActionRequest(InterfaceActionRequest request) {
		System.out.println("Request");
		switch (request.getAction()) {
			case LOAD_SERVER:
				loadServer(request.getProvider());
				break;
			case SHOW_SERVER_SELECTOR:
				displayScreen(new ServerSelectorScreen());
				stage.setWidth(500);
				stage.setHeight(300);
				break;
			case TERMINATE_UI:
				terminate();
				break;
			default:
				System.out.println("Error processing interface action request.");
				break;
		}
	}

	private void loadServer(ServerProvider provider) {
		Engine.setServerProvider(provider);
		final LoadingScreen screen = new LoadingScreen(Engine.getServerLoader());
		screen.registerManager(this);
		displayScreen(screen);
		screen.run();

	}

}
