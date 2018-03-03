package org.ubot.client.provider.loader;

import javafx.concurrent.Task;
import org.ubot.bot.BotModel;
import org.ubot.util.FileDownloader;
import org.ubot.util.injection.Injector;

import java.applet.Applet;
import java.util.List;

public abstract class ServerLoader extends Task<BotModel.Builder> {

	private final String serverName, jarUrl, hookUrl;

	public ServerLoader(String serverName, String jarUrl, String hookUrl) {
		this.serverName = serverName;
		this.jarUrl = jarUrl;
		this.hookUrl = hookUrl;
	}

	@Override
	protected BotModel.Builder call() throws Exception {
		final BotModel.Builder builder = new BotModel.Builder(serverName);
		updateMessage("Updating " + serverName + " jar file.");
		final FileDownloader downloader = new FileDownloader(jarUrl, serverName);
		final Thread downloadThread = new Thread(downloader);
		updateProgress(0.1, 1);
		downloadThread.start();
		while (downloadThread.isAlive()) {
			updateProgress((0.4 * downloader.getProgress()), 1);
		}
		loadHooks(hookUrl);
		return builder;
	}

	@Override
	protected void updateMessage(final String message) {
		super.updateMessage(message);
		System.out.println(message);
	}

	private void loadHooks(String hookUrl) {
		updateMessage("Loading hooks...");
		updateProgress(0.5, 1);
	}

	protected abstract List<Injector> getInjectables();

	protected abstract Applet loadApplet() throws IllegalAccessException;

}
