package org.bot.provider.loader;

import javafx.concurrent.Task;
import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;
import org.bot.hooking.Hook;
import org.bot.provider.overlays.MouseOverlay;
import org.bot.threads.HandleInputs;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.util.FileDownloader;
import org.bot.util.injection.Injector;
import org.bot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerLoader<T extends Component> extends Task<Void> {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private final String HOOK_URL;
	private volatile double progress;
	private volatile String status;
	private FileDownloader downloader = null;
	private Thread inputThreads = null;
	private T component;

	protected ServerLoader(String jarURL, String hookURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
		this.HOOK_URL = hookURL;
	}

	@Override
	public Void call() {
		try {
			updateMessage("Updating " + SERVER_NAME + " jar file.");
			Thread downloadThread = new Thread(this.downloader = new FileDownloader(JAR_URL, SERVER_NAME));
			updateProgress(0.1, 1);
			downloadThread.start();
			while (downloadThread.isAlive()) {
				updateProgress((0.4 * downloader.getProgress()), 1);
			}
			updateMessage("Loading " + SERVER_NAME + " jar file.");
			Engine.getClassArchive().addJar(new File(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar").toURI().toURL());
			updateMessage("Creating reflection engine.");
			Engine.setReflectionEngine(new ReflectionEngine(Engine.getClassArchive(), loadHooks()));
			updateProgress(0.6, 1);
			try {
				updateMessage("Loading component.");
				component = loadComponent();
				Engine.setGameComponent(component);
				updateProgress(0.9, 1);
				if (Engine.getServerProvider().getManifest().type().equals(Applet.class)) {
					updateMessage("Embedding applet.");
					final JPanel panel = new JPanel();
					Applet applet = (Applet) component;
					panel.setPreferredSize(new Dimension(765, 503));
					panel.setLayout(new BorderLayout());
					inputThreads = new Thread(new HandleInputs());
					inputThreads.start();
					Engine.setGameComponent(applet);
					panel.add(applet, BorderLayout.CENTER);
					panel.revalidate();
					Engine.setGameFrame(new GameFrame(panel));
					applet.init();
					if (!applet.isActive()) {
						applet.start();
					}
				} else if (Engine.getServerProvider().getManifest().type().equals(JPanel.class)) {
					updateMessage("Embedding Panel.");
					Engine.setGameFrame(new GameFrame(component));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			updateProgress(1, 1);
			updateMessage(SERVER_NAME + " client started.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateMessage(String message) {
		super.updateMessage(message);
		Logger.log(message, LogType.CLIENT);
	}


	public List<ScreenOverlay> getOverlays() {
		List<ScreenOverlay> overlays = new ArrayList<>();
		overlays.add(new MouseOverlay());
		return overlays;
	}

	public final String getJarURL() {
		return JAR_URL;
	}

	public final String getServerName() {
		return SERVER_NAME;
	}

	private Hook loadHooks() {
		return new Hook(HOOK_URL);
	}

	public abstract List<Injector> getInjectables();

	protected abstract T loadComponent() throws IllegalArgumentException, IllegalAccessException;
}