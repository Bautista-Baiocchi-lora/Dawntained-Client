package org.bot.provider.loader;

import org.bot.Engine;
import org.bot.component.screen.ScreenOverlay;
import org.bot.hooking.Hook;
import org.bot.threads.HandleInputs;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.ui.screens.loading.ProgressRelayer;
import org.bot.ui.screens.loading.ProgressTracker;
import org.bot.util.FileDownloader;
import org.bot.util.injection.Injector;
import org.bot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class ServerLoader<T extends Component> implements Runnable, ProgressRelayer, ProgressTracker {

	private ProgressTracker tracker;
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
	public void run() {
		try {
			status = "Updating " + SERVER_NAME + " jar file.";
			Thread downloadThread = new Thread(this.downloader = new FileDownloader(JAR_URL, SERVER_NAME));
			downloader.registerProgressTracker(this);
			downloadThread.start();
			downloadThread.join();
			status = "Loading " + SERVER_NAME + " jar file.";
			Engine.getClassArchive().addJar(new File(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar").toURI().toURL());
			status = "Creating reflection engine.";
			Engine.setReflectionEngine(new ReflectionEngine(Engine.getClassArchive(), loadHooks()));
			progress += 0.2;
			try {
				status = "Loading component.";
				component = loadComponent();
				progress += 0.2;
				if (Engine.getServerManifest().type().equals(Applet.class)) {
					status = "Embedding applet.";
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
				} else if (Engine.getServerManifest().type().equals(JPanel.class)) {
					status = "Embedding Panel.";
					Engine.setGameComponent(component);
					Engine.setGameFrame(new GameFrame(component));
				}
				progress += 0.2;
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			progress += 0.2;
			status = SERVER_NAME + " client started.";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void registerProgressTracker(ProgressTracker tracker) {
		this.tracker = tracker;
	}

	public void update() {
		if (tracker != null) {
			tracker.update(progress, status);
		}
	}

	public void update(double progress, String status){
		this.progress = (.2 * progress);
		this.status = status;
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

	public abstract JPopupMenu getPopUpMenu();

	public abstract List<Injector> getInjectables();

	public abstract List<ScreenOverlay> getOverlays();

	protected abstract T loadComponent() throws IllegalArgumentException, IllegalAccessException;
}