package org.bot.ui.serverselector;

import org.bot.boot.Engine;
import org.bot.server.ServerLoader;
import org.bot.server.ServerManifest;
import org.bot.server.ServerProvider;
import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        try {
            for (File file : engine.getDirectoryManager().getRootDirectory().getSubDirectory("Server Providers")
                    .getFiles()) {
                jar = new JarFile(file.getAbsolutePath());
                URL[] urls = {new URL("jar:file:" + file.getAbsolutePath() + "!/")};
                ClassLoader cl = URLClassLoader.newInstance(urls);
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry e = entries.nextElement();
                    if (e.getName().endsWith(".class") && !e.getName().contains("$")) {
                        Class<?> clazz;
                        String classPackage = e.getName().replace(".class", "");
                        clazz = cl.loadClass(classPackage.replaceAll("/", "."));
                        ServerLoader<?> loader = null;
                        if (clazz.isAnnotationPresent(ServerManifest.class)) {
                            System.out.println("We found class: " + e.getName());
                            final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
                            if (manifest == null) {
                                System.out.println("Manifest == null");
                            }
                            System.out.println("Provider has been set");
                            loader = (ServerLoader<?>) clazz.newInstance();
                            providers.add(new ServerProvider(loader, manifest));

                        }
                    }
                }
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
