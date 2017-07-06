package org.bot.loader;

import java.awt.Component;
import java.io.IOException;
import java.util.jar.JarFile;

import javax.swing.JFrame;

import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.util.FileDownloader;
import org.bot.util.reflection.ReflectedClass;
import org.objectweb.asm.tree.ClassNode;

public abstract class ServerLoader<T extends Component> {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private final FileDownloader downloader;
	private final ArchiveClassLoader loader;
	private T gameComponent;

	@ServerManifest(author = "Bautista", serverName = "Alora", type = JFrame.class, version = 0.1)
	public ServerLoader(String jarURL, String serverName) throws IOException {
		this.JAR_URL = jarURL;
		this.SERVER_NAME = serverName;
		System.out.println("Updating " + serverName + " jar file.");
		this.downloader = new FileDownloader(jarURL, SERVER_NAME);
		downloader.run();
		System.out.println("Updated " + serverName + " jar file.");
		final JarFile jar = new JarFile(downloader.getArchivePath() + "/" + SERVER_NAME + ".jar");
		final Archive<ClassNode> archive = new JarArchive(jar);
		loader = new ArchiveClassLoader(archive);
		System.out.println("Loading " + serverName + " jar file.");
		try {
			gameComponent = loadProtocol();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		System.out.println(serverName + " component loaded. Starting client.");
	}

	public T getGameComponent() {
		return gameComponent;
	}

	protected ArchiveClassLoader getLoader() {
		return loader;
	}

	public String getJarURL() {
		return JAR_URL;
	}

	public String getServerName() {
		return SERVER_NAME;
	}

	public ReflectedClass getClass(String name) {
		if (!loader.classes().containsKey(name)) {
			try {
				return new ReflectedClass(loader.loadClass(name));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return new ReflectedClass(loader.classes().get(name));
	}

	public abstract T loadProtocol() throws IllegalArgumentException, IllegalAccessException;
}
