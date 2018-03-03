package org.ubot.client.jarloading;

import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;
import org.ubot.util.directory.DirectoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarLoader<T> {

	private final boolean server;

	public JarLoader(boolean server) {
		this.server = server;
	}

	public HashMap<String, T> loadLocal(String directory) {
		final HashMap<String, T> loaded = new HashMap<>();
		try {
			for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory(directory).getFiles()) {
				final ClassArchive classArchive = new ClassArchive();
				if (file.getAbsolutePath().endsWith(".jar")) {
					classArchive.addJar(file);
					final ASMClassLoader classLoader = new ASMClassLoader(classArchive);
					try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
						JarEntry jarEntry;
						while ((jarEntry = inputStream.getNextJarEntry()) != null) {
							if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
								System.out.println(jarEntry.getName());
								String classPackage = jarEntry.getName().replace(".class", "");
								Class<?> clazz = classLoader.loadClass(classPackage.replaceAll("/", "."));
								if (server) {
									if (clazz.isAnnotationPresent(ServerManifest.class)) {
										final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
										ServerLoader serverLoader = (ServerLoader) clazz.newInstance();
										loaded.put(manifest.serverName(), (T) new ServerProvider(manifest, serverLoader));
										System.out.println("Server Loaded: " + manifest.serverName());
									}
								} else {
									//handle scripts
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loaded;
	}

}
