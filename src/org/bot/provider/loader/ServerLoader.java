package org.bot.provider.loader;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import org.bot.Engine;
import org.bot.classloader.ClassArchive;
import org.bot.component.screen.ScreenOverlay;
import org.bot.hooking.Hook;
import org.bot.threads.HandleInputs;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.util.FileDownloader;
import org.bot.util.directory.DirectoryManager;
import org.bot.util.injection.Injector;
import org.bot.util.reflection.ReflectionEngine;

public abstract class ServerLoader<T extends Component> {

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

	public void executeServer() {

		try {
			System.out.println("Updating " + SERVER_NAME + " jar file.");
			this.downloader = new FileDownloader(JAR_URL, SERVER_NAME);
			downloader.run();
			System.out.println("Creating reflection engine.");
			Engine.getClassArchive().addJar(new File(downloader.getArchivePath() +"/" +SERVER_NAME+ ".jar").toURI().toURL());
			Engine.setReflectionEngine(new ReflectionEngine(Engine.getClassArchive(), loadHooks()));
			System.out.println("Loading " + SERVER_NAME + " jar file.");
			try {
				component = loadComponent();
				if (Engine.getServerManifest().type().equals(Applet.class)) {
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

				} else if(Engine.getServerManifest().type().equals(JPanel.class)) {
					System.out.println("We JPanel up in here");
					Engine.setGameComponent(component);
					Engine.setGameFrame(new GameFrame(component));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println(SERVER_NAME + " client started.");
		} catch (IOException e) {
			e.printStackTrace();
		}
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