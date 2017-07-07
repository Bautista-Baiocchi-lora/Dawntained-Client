package org.bot.ui;

import javax.swing.JFrame;

import org.bot.Engine;
import org.bot.provider.ServerProvider;

public class InterfaceManager {

	private final Engine engine = Engine.getInstance();
	private final BotUI ui;

	public InterfaceManager(BotUI ui) {
		this.ui = ui;
	}

	public void loadServer(ServerProvider provider) {
		engine.setServerProvider(provider);
		if (provider.getManifest().type().equals(JFrame.class)) {
			ui.terminate();
		}
		engine.getServerLoader().executeServer();
	}

	public void startServerSelector() {
		ui.displayServerSelector();
	}

	public boolean logIn(String username, String password, boolean rememberMe) {
		if (username.equals("ethan") && password.equals("123")) {
			engine.setUsername(username);
			engine.setDeveloper(true);
			ui.displayServerSelector();
			return true;
		}
		/**
		 * HANDLE WEB-BASED LOGIN HERE.
		 */
		return false;
	}
}
