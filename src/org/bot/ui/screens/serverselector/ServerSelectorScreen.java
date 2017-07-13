package org.bot.ui.screens.serverselector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.bot.Engine;
import org.bot.classloader.ASMClassLoader;
import org.bot.classloader.ClassArchive;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.NullManifestException;
import org.bot.provider.manifest.ServerManifest;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ServerSelectorScreen extends Scene {

	private static HBox layout;
	private ServerInformationTab serverTab;

	public ServerSelectorScreen() {
		super(layout = new HBox());
		configure();
	}

	private void configure() {
		ListView<ServerLabel> list = new ListView<ServerLabel>();
		list.setEditable(false);
		ArrayList<ServerLabel> providers = getServerProviderComponents();
		ObservableList<ServerLabel> items = FXCollections.observableArrayList(providers);
		list.setItems(items);
		list.setOnMouseClicked((e) -> {
			if (list.getSelectionModel().getSelectedItem() != null) {
				displayTab(list.getSelectionModel().getSelectedItem().getTab());
			}
		});
		list.setMaxWidth(250);
		layout.getChildren().addAll(list);
	}

	private ArrayList<ServerLabel> getServerProviderComponents() {
		final ArrayList<ServerLabel> providers = new ArrayList<ServerLabel>();
		JarInputStream inputStream = null;
		try {
			for (File file : Engine.getDirectoryManager().getRootDirectory().getSubDirectory("Server Providers")
					.getFiles()) {
				Engine.setClassArchive(new ClassArchive());
				Engine.getClassArchive().addJar((new File(file.getAbsolutePath()).toURI().toURL()));
				ASMClassLoader cl = new ASMClassLoader(Engine.getClassArchive());
				inputStream = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				while ((jarEntry = inputStream.getNextJarEntry()) != null) {
					if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
						Class<?> clazz;
						String classPackage = jarEntry.getName().replace(".class", "");
						clazz = cl.loadClass(classPackage.replaceAll("/", "."));
						ServerLoader<?> loader = null;
						if (clazz.isAnnotationPresent(ServerManifest.class)) {
							final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
							if (manifest == null) {
								throw new NullManifestException();
							}
							loader = (ServerLoader<?>) clazz.newInstance();
							Engine.getProviderJarNames().put(manifest.serverName(), file.getName());
							providers.add(new ServerLabel(loader, manifest));
						}
					}
				}
			}
			inputStream.close();
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException
				| InvalidDirectoryNameException e) {
			e.printStackTrace();
		} catch (NullManifestException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		return providers;
	}

	private void displayTab(ServerInformationTab tab) {
		if (serverTab == null) {
			serverTab = tab;
			layout.getChildren().add(serverTab);
		} else {
			layout.getChildren().remove(serverTab);
			serverTab = tab;
			layout.getChildren().add(serverTab);
		}
	}

}
