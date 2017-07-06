package org.bot.ui.serverselector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.JarFile;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

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
	private static ServerSelector instance;
	private final ArrayList<ServerProvider> providers;
	private final JPanel view;

	public ServerSelector() {
		super("Server Selector");
		setSize(400, 300);
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
			view.add(new ServerProviderComponent(provider));
		}
		this.add(scrollerPane);
		instance = this;
	}

	private static class ServerProviderComponent extends JComponent {

		private static final long serialVersionUID = 8365360607486641452L;
		private final Font NAME_FONT = new Font("Dialog", Font.BOLD, 18);
		private final Font INFO_FONT = new Font("Dialog", Font.ITALIC, 13);
		private final Font DESCRIPTION_FONT = new Font("Dialog", Font.PLAIN, 11);
		private final Border BORDER = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Description");
		private final Border COMPONENT_BORDER = BorderFactory.createLineBorder(Color.BLACK);
		private final ServerProvider provider;
		private final JLabel author, serverName, type, version;

		public ServerProviderComponent(ServerProvider provider) {
			super();
			setBorder(COMPONENT_BORDER);
			this.provider = provider;
			setLayout(new FlowLayout());
			author = new JLabel("Author: " + provider.getManifest().author());
			author.setFont(INFO_FONT);
			serverName = new JLabel(provider.getManifest().serverName());
			serverName.setFont(NAME_FONT);
			type = new JLabel("Type: " + provider.getManifest().type().getSimpleName());
			type.setFont(INFO_FONT);
			version = new JLabel("Version: " + provider.getManifest().version());
			version.setFont(INFO_FONT);
			setPreferredSize(new Dimension(400, 150));
			add(serverName);
			add(author);
			add(type);
			add(version);
			setBackground(Color.GRAY);
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					engine.setServerProvider(provider);
					instance.dispose();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});
		}

	}

}
