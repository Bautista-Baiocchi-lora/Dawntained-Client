package org.bot.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bot.Engine;
import org.bot.provider.ServerProvider;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manageable;
import org.bot.ui.management.Manager;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.ui.screens.loading.LoadingScreen;
import org.bot.ui.screens.login.PortalScreen;
import org.bot.ui.screens.serverselector.ServerSelectorScreen;
import org.bot.util.ConfigManager;
import org.bot.util.directory.DirectoryManager;

public class BotUI extends Application implements Manager {

	public static Stage stage;
	private static BotUI instance;

	public BotUI() {
		Engine.setDirectoryManager(new DirectoryManager());
		Engine.setConfigManager(new ConfigManager());
		Engine.getDirectoryManager().loadServerProviderJars();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static BotUI getInstance() {
		return instance == null ? instance = new BotUI() : instance;
	}

	private void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		displayScreen(new PortalScreen());
		stage.show();
		Logger.log("Client user interface started.", LogType.CLIENT);
	}

	private void displayScreen(final Scene scene) {
		if (scene instanceof Manageable) {
			((Manageable) scene).registerManager(this);
			Logger.log("UI Manager registered.", LogType.DEBUG);
		}
		stage.setTitle(Engine.getInterfaceTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		Logger.log("Scene changed.", LogType.DEBUG);
	}

	@Override
	public void processActionRequest(InterfaceActionRequest request) {
		Logger.log("Action requested: " + request.getAction().toString(), LogType.DEBUG);
		switch (request.getAction()) {
			case BLOCK_MAIN_STAGE:
				stage.initModality(Modality.APPLICATION_MODAL);
				break;
			case LOAD_SERVER:
				loadServer(request.getProvider());
				break;
			case SHOW_SERVER_SELECTOR:
				displayScreen(new ServerSelectorScreen());
				resizeStage(250, 300);
				break;
			case TERMINATE_UI:
				terminate();
				break;
			case RESIZE_STAGE:
				resizeStage(request.getStageWidth(), request.getStageHeight());
				break;
			default:
				Logger.logException("Error processing interface action request.", LogType.CLIENT);
				break;
		}
	}

	private void resizeStage(double width, double height) {
		if (stage != null) {
			stage.setWidth(width);
			stage.setHeight(height);
			Logger.log("Resize stage to: " + width + ", " + height, LogType.DEBUG);
		}
	}

	private void loadServer(ServerProvider provider) {
		Engine.setServerProvider(provider);
		final LoadingScreen screen = new LoadingScreen(Engine.getServerProvider().getLoader());
		screen.registerManager(this);
		displayScreen(screen);
		screen.run();
	}

}
