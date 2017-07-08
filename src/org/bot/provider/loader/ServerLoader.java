package org.bot.provider.loader;

import java.applet.Applet;
import java.awt.Component;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

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

import javax.swing.*;

public abstract class ServerLoader<T extends Component> extends ReflectionEngine {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private FileDownloader downloader = null;

	public ServerLoader(String jarURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
	}

	public void executeServer() {
		try {
			getHooks();
			System.out.println("Updating " + SERVER_NAME + " jar file.");
			this.downloader = new FileDownloader(JAR_URL, SERVER_NAME);
			downloader.run();
			System.out.println("Updated " + SERVER_NAME + " jar file.");
			final JarFile jar = new JarFile(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar");
			final Archive<ClassNode> archive = new JarArchive(jar);
			Engine.getInstance().setClassLoader(new ArchiveClassLoader(archive));
			System.out.println("Loading " + SERVER_NAME + " jar file.");
			try {
				Engine.getInstance().setGameComponent(loadProtocol());
				if(Engine.getInstance().getServerManifest().type().equals(JPanel.class)) {
					Engine.getInstance().setGameFrame(new GameFrame(Engine.getInstance().getGameComponent()));
					Engine.getInstance().getGameFrame().setVisible(true);
				} else {

					/**
					 * Handle the adding to jframe?
					 */
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println(SERVER_NAME + " component loaded. Starting client.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJarURL() {
		return JAR_URL;
	}

	public String getServerName() {
		return SERVER_NAME;
	}

	protected abstract Hook getHooks();

	public abstract List<ScreenOverlay> getOverlays();

	protected abstract T loadProtocol() throws IllegalArgumentException, IllegalAccessException;
}