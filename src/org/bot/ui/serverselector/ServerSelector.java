package org.bot.ui.serverselector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.bot.boot.Engine;
import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.server.ServerLoader;
import org.bot.server.ServerManifest;
import org.bot.server.ServerProvider;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;
import org.objectweb.asm.tree.ClassNode;

public class ServerSelector extends JFrame {

	private static final long serialVersionUID = 8839153897939669912L;
	private static final Engine engine = Engine.getInstance();
	private final ArrayList<ServerProvider> providers;
	private final JPanel view;

	public ServerSelector() {
		super("Server Selector");
		setSize(400, 150);
		this.providers = new ArrayList<ServerProvider>();
		JarFile jar;
		ArchiveClassLoader jarLoader;
		try {
			for (File file : engine.getDirectoryManager().getRootDirectory().getSubDirectory("Server Providers")
					.getFiles()) {
				jar = new JarFile(file.getAbsolutePath());
				final Archive<ClassNode> archive = new JarArchive(jar);
				jarLoader = new ArchiveClassLoader(archive);
				final Class<? extends ServerLoader> loaderClass = (Class<? extends ServerLoader>) jarLoader
						.loadClass("Loader");
				ServerLoader<?> loader = null;
				final ServerManifest manifest = (ServerManifest) loaderClass.getAnnotation(ServerManifest.class);
				if (manifest.type().getClass().equals(JFrame.class)) {
					loader = (ServerLoader<JFrame>) loaderClass.newInstance();
				}
				providers.add(new ServerProvider(loader, manifest));
			}
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException
				| InvalidDirectoryNameException e) {
			e.printStackTrace();
		}
		final JScrollPane scrollerPane = new JScrollPane(view = new JPanel());
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		scrollerPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		for (ServerProvider provider : providers) {
			view.add(new ServerProviderComponent(provider, this));
		}
		this.add(scrollerPane);
	}
}
