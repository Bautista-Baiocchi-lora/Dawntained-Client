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

	private static BotUI instance = new BotUI();
	private Stage stage;

	public BotUI() {
		Engine.setDirectoryManager(new DirectoryManager());
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static BotUI getInstance() {
		return instance;
	}

	public void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle(Engine.getInterfaceTitle());
		stage.show();
		final PortalScreen portal = new PortalScreen();
		portal.registerManager(this);
		stage.setScene(portal);
		stage.sizeToScene();
		this.stage = stage;
	}

	private void displayServerSelector() {
		displayScreen(new ServerSelectorScreen());
		stage.setWidth(500);
		stage.setHeight(300);
	}

	private void displayScreen(final Scene scene) {
		final Stage newStage = new Stage();
		newStage.setTitle(Engine.getInterfaceTitle());
		newStage.setScene(scene);
		newStage.sizeToScene();
		if (stage != null) {
			this.stage.close();
		}
		this.stage = newStage;
		this.stage.show();
	}

	@Override
	public void processActionRequest(InterfaceActionRequest request) {
		switch (request.getAction()) {
			case LOAD_SERVER:
				loadServer(request.getProvider());
				break;
			case SHOW_SERVER_SELECTOR:
				displayServerSelector();
				break;
			case TERMINATE_UI:
				terminate();
				break;
			case TERMINATE_CURRENT_STAGE:
				stage = null;
				break;
			default:
				System.out.println("Error processing interface aciton request.");
				break;
		}
	}

	private void loadServer(ServerProvider provider) {
		Engine.setServerProvider(provider);
		final LoadingScreen screen = new LoadingScreen(Engine.getServerLoader());
		displayScreen(screen);
		screen.run();
	}

}
