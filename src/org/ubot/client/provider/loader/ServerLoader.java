package org.ubot.client.provider.loader;

import javafx.concurrent.Task;
import org.ubot.bot.BotModel;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.util.FileDownloader;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.util.List;

public abstract class ServerLoader extends Task<BotModel.Builder> {

	private final String serverName, jarUrl, hookUrl;

	public ServerLoader(String jarUrl, String hookUrl, String serverName) {
		this.serverName = serverName;
		this.jarUrl = jarUrl;
		this.hookUrl = hookUrl;
	}

	@Override
	protected BotModel.Builder call() throws Exception {
		final BotModel.Builder builder = new BotModel.Builder(serverName);
		updateMessage("Updating " + serverName + " jar file.", 0.1);
		final FileDownloader downloader = new FileDownloader(jarUrl, serverName);
		final Thread downloadThread = new Thread(downloader);
		downloadThread.start();
		while (downloadThread.isAlive()) {
			updateProgress((0.4 * downloader.getProgress()), 1);
		}
		loadHooks(hookUrl);
		updateMessage("Invoking client...", 0.5);
		final ClassArchive classArchive = new ClassArchive();
		classArchive.addJar(new File(downloader.getArchivePath() + "/" + serverName + ".jar"));
		updateMessage("Injecting...", 0.6);
		final ASMClassLoader asmClassLoader = new ASMClassLoader(classArchive);
		//TODO: Make classloader inject.
		updateMessage("Starting reflection engine...", 0.7);
		final ReflectionEngine reflectionEngine = new ReflectionEngine(asmClassLoader);
		builder.reflectionEngine(reflectionEngine);
		updateMessage("Loading applet...", 0.8);
		final Applet applet = loadApplet();
		updateMessage("Embedding applet...", 0.9);
		builder.applet(applet);
		final JPanel panel = embedApplet(applet);
		builder.panel(panel);
		return builder;
	}

	private final JPanel embedApplet(Applet applet) {
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(765, 503));
		panel.setLayout(new BorderLayout());
		if (applet != null) {
			panel.add(applet, BorderLayout.CENTER);
			panel.revalidate();
			applet.init();
			if (!applet.isActive()) {
				applet.start();
			}
		} else {
			panel.add(new JLabel("Error: Corrupted Applet"));
		}
		return panel;
	}

	@Override
	protected void updateMessage(final String message) {
		super.updateMessage(message);
		System.out.println(message);
	}

	protected void updateMessage(final String message, double percent) {
		updateMessage(message);
		updateProgress(percent, 1);
	}

	private void loadHooks(String hookUrl) {
		updateMessage("Loading hooks...", 0.5);
		//logic
	}

	protected abstract List<Injector> getInjectables();

	protected abstract Applet loadApplet() throws IllegalAccessException;

}
