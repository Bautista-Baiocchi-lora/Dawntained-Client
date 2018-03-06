package org.ubot.bot;

import org.ubot.client.Client;
import org.ubot.client.account.Account;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.ui.logger.Logger;
import org.ubot.client.ui.screens.BotConfigurationScreen;
import org.ubot.client.ui.screens.BotLoadingScreen;
import org.ubot.client.ui.screens.BotScreen;
import org.ubot.component.RSCanvas;

import javax.swing.*;
import java.applet.Applet;
import java.util.ArrayList;

public class Bot {

	private final Client client;
	private String name;
	private Account account;
	private JPanel view;
	private BotCore core;
	private boolean debugSettings, debugGameInfo, debugNPCs, debugPlayer, debugObjects, debugInventory;

	public Bot(Client client, String name) {
		this.client = client;
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public void toggleObjectsDebug() {
		this.debugObjects = !debugObjects;
		Logger.log(Boolean.toString(debugObjects));
	}

	public void togglePlayerDebug() {
		this.debugPlayer = !debugPlayer;
	}

	public void toggleNPCsDebug() {
		this.debugNPCs = !debugNPCs;
	}

	public void toggleInventoryDebug() {
		this.debugInventory = !debugInventory;
	}

	public void toggleSettingsDebug() {
		this.debugSettings = !debugSettings;
	}

	public void toggleGameInfoDebug() {
		this.debugGameInfo = !debugGameInfo;
	}

	public boolean isDebugSettings() {
		return debugSettings;
	}

	public boolean isDebugGameInfo() {
		return debugGameInfo;
	}

	public boolean isDebugNPCs() {
		return debugNPCs;
	}

	public boolean isDebugPlayer() {
		return debugPlayer;
	}

	public boolean isDebugObjects() {
		return debugObjects;
	}

	public boolean isDebugInventory() {
		return debugInventory;
	}

	public String getName() {
		return name;
	}

	public JPanel getView() {
		return view;
	}

	public RSCanvas getGameCanvas() {
		if (core == null) {
			return null;
		}
		return core.getGameCanvas();
	}

	public Applet getApplet() {
		if (core == null) {
			return null;
		}
		return core.getApplet();
	}

	public void launch(BotCore core) {
		this.core = core;
		this.view = new BotScreen(this);
		client.displayScreen(view);
	}

	public void initiateConfiguration(ArrayList<ServerProvider> providers) {
		this.view = new BotConfigurationScreen(this, providers);
	}

	public void initiateServerLoader(ServerLoader loader) {
		final BotLoadingScreen loadingScreen = new BotLoadingScreen(this, loader);
		this.view = loadingScreen;
		client.displayScreen(this);
		loadingScreen.run();
	}
}
