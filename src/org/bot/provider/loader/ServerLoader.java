package org.bot.provider.loader;

import java.awt.Component;
import java.io.IOException;
import java.util.jar.JarFile;

import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.util.FileDownloader;
import org.bot.util.reflection.ReflectedClass;
import org.objectweb.asm.tree.ClassNode;

public abstract class ServerLoader<T extends Component> {

	private final String JAR_URL;
	private final String SERVER_NAME;
	private FileDownloader downloader = null;
	private ArchiveClassLoader loader = null;
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
			loader = new ArchiveClassLoader(archive);
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