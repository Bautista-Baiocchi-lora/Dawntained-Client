package org.bot.provider.loader;

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
import org.bot.util.FileDownloader;
import org.bot.util.reflection.ReflectionEngine;
import org.objectweb.asm.tree.ClassNode;


public abstract class ServerLoader<T extends Component> extends ReflectionEngine {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private FileDownloader downloader = null;

	private T gameComponent;
	public ServerLoader(String jarURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
	}

	public void executeServer() {
		try {
			System.out.println("Updating " + SERVER_NAME + " jar file.");
			this.downloader = new FileDownloader(JAR_URL, SERVER_NAME);
			downloader.run();
			System.out.println("Updated " + SERVER_NAME + " jar file.");
			final JarFile jar = new JarFile(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar");
			final Archive<ClassNode> archive = new JarArchive(jar);
			Engine.getInstance().setClassLoader(new ArchiveClassLoader(archive));
			System.out.println("Loading " + SERVER_NAME + " jar file.");
			try {
				gameComponent = loadProtocol();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println(SERVER_NAME + " component loaded. Starting client.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public T getGameComponent() {
		return gameComponent;
	}

	public String getJarURL() {
		return JAR_URL;
	}

	public String getServerName() {
		return SERVER_NAME;
	}

	public abstract Hook getHooks();

	public abstract List<ScreenOverlay> getOverlays();

	public abstract T loadProtocol() throws IllegalArgumentException, IllegalAccessException;
}