package org.ubot.bot.script.loader;

import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.script.scriptdata.ScriptManifest;
import org.ubot.bot.script.types.LoopScript;
import org.ubot.client.classloader.ASMClassLoader;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.manifest.NullManifestException;
import org.ubot.util.directory.DirectoryManager;
import org.ubot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class ScriptLoader {
	private static final List<ScriptData> scripts = new ArrayList<>();

	public static List<ScriptData> getScripts() {
		//TODO: SDN scripts too
		return getLocalJarScripts();
	}

	private static ArrayList<ScriptData> getLocalJarScripts() {
		final ArrayList<ScriptData> scripts = new ArrayList<>();
		JarInputStream inputStream = null;
		try {
			for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory(DirectoryManager.SCRIPTS).getFiles()) {
				final ClassLoader classLoader = new URLClassLoader(new URL[] {file.toURI().toURL()});
				inputStream = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				while ((jarEntry = inputStream.getNextJarEntry()) != null) {
					if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
						Class<?> clazz;
						String classPackage = jarEntry.getName().replace(".class", "");
						clazz = classLoader.loadClass(classPackage.replaceAll("/", "."));
						if (clazz.isAnnotationPresent(ScriptManifest.class)) {
							System.out.println("Found a script");
							final ScriptManifest manifest = clazz.getAnnotation(ScriptManifest.class);
							if (manifest == null) {
								throw new NullManifestException();
							}
							ScriptData scriptData = new ScriptData(classPackage.replaceAll("/", "."), manifest.name(), manifest.server(), manifest.description(), manifest.version(), manifest.author(), manifest.category(), new File(file.getAbsolutePath()));
							scripts.add(scriptData);
							System.out.println("added a script");

						}
					}
				}
			}
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException | ClassNotFoundException
				| InvalidDirectoryNameException e) {
			e.printStackTrace();
		} catch (NullManifestException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		return scripts;
	}

}
