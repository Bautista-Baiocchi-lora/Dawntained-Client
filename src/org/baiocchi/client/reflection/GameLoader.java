package org.baiocchi.client.reflection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.baiocchi.client.util.Cache;

public class GameLoader extends URLClassLoader {

	private final HashMap<String, byte[]> classes;

	public GameLoader() throws MalformedURLException {
		super(new URL[] { new File(Cache.getJarPath()).toURI().toURL() });
		classes = new HashMap<String, byte[]>();
		loadClientJar();
	}

	@Override
	public Class<?> loadClass(String name) {
		if (classes.containsKey(name)) {
			byte[] value = classes.get(name);
			return defineClass(name, value, 0, value.length);
		}
		try {
			return super.loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void loadClientJar() {
		System.out.println("Loading classes...");
		try (JarInputStream jarInStream = new JarInputStream(new FileInputStream(new File(Cache.getJarPath())))) {
			JarEntry entry;
			while ((entry = jarInStream.getNextJarEntry()) != null) {
				if (!entry.getName().contains(".class")) {
					continue;
				}
				try (ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream()) {
					byte[] data = new byte[1024];
					int read;
					while ((read = jarInStream.read(data, 0, 1024)) > 0) {
						byteOutStream.write(data, 0, read);
					}
					classes.put(entry.getName().replace(".class", ""), byteOutStream.toByteArray());
					int percent = (classes.size() * 100) / 124;
					if (entry.getName().contains("class")) {
						System.out.println("[" + percent + "% Done] Loaded: " + entry.getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done loading classes!");
	}

}
