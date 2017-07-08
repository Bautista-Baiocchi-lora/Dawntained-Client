package org.bot.ui;

import javax.swing.*;

import org.bot.Engine;
import org.bot.provider.ServerProvider;
import org.bot.ui.management.InterfaceActionRequest;
import org.bot.ui.management.Manager;
import org.bot.ui.screens.login.PortalScreen;
import org.bot.ui.screens.serverselector.ServerSelectorScreen;
import org.bot.util.directory.DirectoryManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BotUI extends Application implements Manager {

	private Stage stage;
	private static BotUI instance = new BotUI();

	public static void main(String[] args) {
		launch(args);
	}

	public BotUI() {
		Engine.getInstance().setDirectoryManager(new DirectoryManager());
	}

	public static BotUI getInstance() {
		return instance;
	}

	private void terminate() {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("uBot v" + Engine.VERSION);
		stage.show();
		PortalScreen portal = new PortalScreen();
		portal.registerManager(this);
		stage.setScene(portal);
		this.stage = stage;
	}

	private void displayServerSelector() {
		displayScreen(new ServerSelectorScreen());
		stage.setWidth(500);
		stage.setHeight(300);
	}

	private void displayScreen(final Scene scene) {
		if (stage != null) {
			stage.setScene(scene);
		}
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
		default:
			System.out.println("Error processing interface aciton request.");
			break;
		}
	}

	private void loadServer(ServerProvider provider) {
		Engine.getInstance().setServerProvider(provider);
		if (provider.getManifest().type().equals(JFrame.class) || provider.getManifest().type().equals(JPanel.class)) {
			terminate();
		}
		Engine.getInstance().getServerLoader().executeServer();
	}

}
