package org.bot.loader;

import org.bot.api.reflection.Modifiers;
import org.bot.api.reflection.ReflectedClass;
import org.bot.api.reflection.ReflectedField;
import org.bot.boot.Constants;
import org.bot.boot.Engine;
import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.ui.menu.ButtonPanel;
import org.bot.util.FileDownloader;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.jar.JarFile;

public class GameLoader {

    private final FileDownloader downloader;
    private ArchiveClassLoader loader;
    private ButtonPanel buttonPanel;

    public GameLoader() {
        downloader = new FileDownloader(Constants.JAR_URL, Constants.getJarPath());
        downloader.run();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JarFile jar = new JarFile(Constants.getJarPath());
                    final Archive<ClassNode> archive = new JarArchive(jar);
                    final ArchiveClassLoader loader = GameLoader.this.loader = new ArchiveClassLoader(archive);
                    final ReflectedClass clientLoader = GameLoader.this.getClass("ClientLoader")
                            .getConstructor(
                                    new Modifiers.ModifierBuilder().parameterTypes(String.class, boolean.class).build())
                            .getNewInstance("1", true);
                    final ReflectedField frameField = clientLoader
                            .getField(new Modifiers.ModifierBuilder().name("add").build());
                    final JFrame frame = (JFrame) frameField.getValue();
                    frame.setTitle("Fuck alora for ip banning me");
                    frame.revalidate();
                    buttonPanel = new ButtonPanel();
                    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
                    frame.pack();
                    frameField.setValue(frame);
                    Engine.getInstance().setGameJFrame(frame);
                } catch (IOException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
}