package org.baiocchi.client.reflection.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ReflectionEngine extends URLClassLoader {

	private final HashMap<String, byte[]> classes;
	private final String jarPath;

	public ReflectionEngine(String jarPath) throws MalformedURLException {
		super(new URL[] { new File(jarPath).toURI().toURL() });
		this.jarPath = jarPath;
		classes = new HashMap<String, byte[]>();
		loadClientJar();
	}

	public ReflectedClass getClass(String name) {
		return new ReflectedClass(loadClass(name));
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
		try (JarInputStream jarInStream = new JarInputStream(new FileInputStream(new File(jarPath)))) {
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
	}

}
