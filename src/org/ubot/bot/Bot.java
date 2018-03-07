package org.ubot.bot;

import org.ubot.bot.component.RSCanvas;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.bot.script.ScriptHandler;
import org.ubot.client.Client;
import org.ubot.client.account.Account;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.ui.screens.BotConfigurationScreen;
import org.ubot.client.ui.screens.BotLoadingScreen;
import org.ubot.client.ui.screens.BotScreen;

import javax.swing.*;
import java.applet.Applet;
import java.util.ArrayList;
import java.util.List;

public class Bot {

	private final Client client;
	private String name;
	private Account account;
	private JPanel view;
	private BotCore core;
	private ScriptHandler scriptHandler;

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

	public List<ScreenOverlay> getScreenOverlays() {
		if (core == null) {
			return new ArrayList<>();
		}
		return core.getScreenOverlays();
	}

	public String getBotName() {
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
		client.displayScreen(this);
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
