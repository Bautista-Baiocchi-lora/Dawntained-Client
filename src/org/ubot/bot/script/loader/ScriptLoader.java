package org.ubot.bot.script.loader;

import org.ubot.bot.Bot;
import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.script.scriptdata.ScriptManifest;
import org.ubot.bot.script.types.LoopScript;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.ClientModel;
import org.ubot.client.provider.ServerProvider;
import org.ubot.util.directory.DirectoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by Ethan on 3/7/2018.
 */
public class ScriptLoader {
	private final List<ScriptData> scripts = new ArrayList<>();
	private final Bot bot;
	private final ClientModel model;

	public ScriptLoader(ClientModel model, Bot bot) {
		this.model = model;
		this.bot = bot;
	}

	public List<ScriptData> getScripts() {
		scripts.clear();
		for (ScriptData scriptData : loadLocalScripts()) {
			scripts.add(scriptData);
		}

		return scripts;
	}

	private final List<ScriptData> loadLocalScripts() {
		final List<ScriptData> scripts = new ArrayList<>();
		try {
			for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory(DirectoryManager.SCRIPTS).getFiles()) {
				final ClassArchive classArchive = new ClassArchive();
				if (file.getAbsolutePath().endsWith(".jar")) {
					classArchive.addJar(file);
					final ASMClassLoader classLoader = new ASMClassLoader(classArchive);
					try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
						JarEntry jarEntry;
						while ((jarEntry = inputStream.getNextJarEntry()) != null) {
							if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
								String classPackage = jarEntry.getName().replace(".class", "");
								Class<?> clazz = classLoader.loadClass(classPackage.replaceAll("/", "."));
								if (clazz.isAnnotationPresent(ScriptManifest.class)) {
									System.out.println("Loading Server");
									final ScriptManifest manifest = clazz.getAnnotation(ScriptManifest.class);
									ScriptData scriptData = new ScriptData(classPackage.replaceAll("/", "."), manifest.name(), manifest.server(), manifest.description(), manifest.version(), manifest.author(), manifest.category(), new File(file.getAbsolutePath()));
									scripts.add(scriptData);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scripts;
	}

	public final LoopScript loadScript(ScriptData scriptData) {
		LoopScript loopScript = null;
		String clazz = null;
		try {
			bot.getClassArchive().addJar(new File(scriptData.scriptPath.getAbsolutePath()));
			for (Map.Entry<File, ServerProvider> providerEntry : model.getProviders().entrySet()) {
				if (providerEntry.getValue().getManifest().serverName().equals(bot.getServerName())) {
					bot.getClassArchive().addJar(providerEntry.getKey());
					clazz = providerEntry.getValue().getMainClass().getCanonicalName();
				}
			}

			bot.getReflectionEngine().setScriptReflEngine(clazz, bot.getReflectionEngine());
			loopScript = (LoopScript) bot.getReflectionEngine().getClass(scriptData.clazz).getNewInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loopScript;
	}
}
