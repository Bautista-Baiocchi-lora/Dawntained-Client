package org.ubot.client.provider.loader;

import javafx.concurrent.Task;

import java.applet.Applet;

public abstract class ServerLoader extends Task<Void> {

	private final String serverName, jarUrl, hookUrl;

	protected ServerLoader(String serverName, String jarUrl, String hookUrl) {
		this.serverName = serverName;
		this.jarUrl = jarUrl;
		this.hookUrl = hookUrl;
	}

	@Override
	protected Void call() throws Exception {
		return null;
	}

	@Override
	protected void updateMessage(final String message) {
		super.updateMessage(message);
		System.out.println(message);
	}

	private void loadHooks() {
		System.out.println("Hooks loaded.");
	}

	protected abstract Applet loadApplet() throws IllegalAccessException;

}
