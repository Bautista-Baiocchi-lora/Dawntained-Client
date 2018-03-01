package org.ubot.client.provider.loader;

import javafx.concurrent.Task;
import org.ubot.bot.BotModel;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.client.classloader.ASMClassLoader;
import org.ubot.client.classloader.ClassArchive;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.client.hooking.Hook;
import org.ubot.bot.component.screen.overlays.MouseOverlay;
import org.ubot.client.provider.threads.HandleInputs;
import org.ubot.util.FileDownloader;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerLoader<T extends Component> extends Task<BotModel.Builder> {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private final String HOOK_URL;
	private FileDownloader downloader = null;
	private Thread inputThreads = null;
	private T component;

	protected ServerLoader(String jarURL, String hookURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
		this.HOOK_URL = hookURL;
	}

	@Override
	public BotModel.Builder call() {
		final BotModel.Builder botBuilder = new BotModel.Builder();
		try {
			updateMessage("Updating " + SERVER_NAME + " jar file.");
			Thread downloadThread = new Thread(this.downloader = new FileDownloader(JAR_URL, SERVER_NAME));
			updateProgress(0.1, 1);
			downloadThread.start();
			while (downloadThread.isAlive()) {
				updateProgress((0.4 * downloader.getProgress()), 1);
			}
			updateMessage("Loading " + SERVER_NAME + " jar file.");
			final ClassArchive classArchive = new ClassArchive();
			classArchive.addJar(new File(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar").toURI().toURL());
			botBuilder.setClassArchive(classArchive);
			updateMessage("Injecting callbacks.");
			updateProgress(0.5, 1);
			final ASMClassLoader classLoader = new ASMClassLoader(classArchive, getInjectables());
			botBuilder.setASMClassLoader(classLoader);
			updateMessage("Creating reflection engine.");
			final ReflectionEngine reflectionEngine = new ReflectionEngine(classLoader, loadHooks());
			botBuilder.setReflectionEngine(reflectionEngine);
			updateProgress(0.6, 1);
			try {
				updateMessage("Loading component.");
				component = loadComponent();
				updateProgress(0.9, 1);
				if (component instanceof Applet) {
					updateMessage("Embedding applet.");
					final JPanel panel = new JPanel();
					final Applet applet = (Applet) component;
					panel.setPreferredSize(new Dimension(765, 503));
					panel.setLayout(new BorderLayout());
					inputThreads = new Thread(new Runnable() {
						@Override
						public void run() {

						}
					});
					inputThreads.start();
					panel.add(applet, BorderLayout.CENTER);
					panel.revalidate();
					botBuilder.setGameComponent(panel);
					applet.init();
					if (!applet.isActive()) {
						applet.start();
					}
				} else if (component instanceof JPanel) {
					updateMessage("Embedding Panel.");
					botBuilder.setGameComponent(component);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			updateProgress(1, 1);
			updateMessage(SERVER_NAME + " client loaded successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return botBuilder;
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