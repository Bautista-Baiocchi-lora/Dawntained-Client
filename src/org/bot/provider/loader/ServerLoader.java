package org.bot.provider.loader;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

import javax.swing.JPanel;

import org.bot.Engine;
import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.component.screen.ScreenOverlay;
import org.bot.hooking.Hook;
import org.bot.ui.screens.clientframe.GameFrame;
import org.bot.util.FileDownloader;
import org.bot.util.reflection.ReflectionEngine;
import org.objectweb.asm.tree.ClassNode;

public abstract class ServerLoader<T extends Component> {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private final String HOOK_URL;
	private Hook hooks;
	private FileDownloader downloader = null;

	public ServerLoader(String jarURL, String hookURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
		this.HOOK_URL = hookURL;
	}

	public void executeServer() {
		try {
			System.out.println("Loading hooks file.");
			loadHooks(HOOK_URL);
			System.out.println("Updating " + SERVER_NAME + " jar file.");
			this.downloader = new FileDownloader(JAR_URL, SERVER_NAME);
			downloader.run();
			final JarFile jar = new JarFile(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar");
			final Archive<ClassNode> archive = new JarArchive(jar);
			Engine.setClassLoader(new ArchiveClassLoader(archive));
			System.out.println("Loading " + SERVER_NAME + " jar file.");
			try {
				T component = loadComponent();
				if (Engine.getServerManifest().type().equals(Applet.class)) {
					Applet applet = (Applet) component;
					applet.setPreferredSize(new Dimension(765, 503));
					applet.init();
					if (!applet.isActive()) {
						applet.start();
					}
					Engine.setGameComponent(applet);
					final JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());
					panel.add(applet, BorderLayout.CENTER);
					panel.revalidate();
					Engine.setGameFrame(new GameFrame(panel));
				} else {

					/**
					 * Handle the adding to jframe?
					 */
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

	public final Hook getHooks() {
		return hooks;
	}

	private void loadHooks(String url) {
		hooks = new Hook(url);
	}

	public abstract List<ScreenOverlay> getOverlays();

	protected abstract T loadComponent() throws IllegalArgumentException, IllegalAccessException;
}