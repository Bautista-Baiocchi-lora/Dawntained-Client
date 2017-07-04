package org.bot.loader;

import org.bot.boot.Engine;
import org.bot.classloader.Archive;
import org.bot.classloader.ArchiveClassLoader;
import org.bot.classloader.JarArchive;
import org.bot.boot.Constants;
import org.bot.ui.ButtonPanel;
import org.bot.util.FileDownloader;
import org.objectweb.asm.tree.ClassNode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.jar.JarFile;

public class GameLoader {
    private Engine engine = Engine.getInstance();
    private FileDownloader downloader;
    public ClassLoader classLoader;
    private ButtonPanel buttonPanel;

    public GameLoader() {
        downloader = new FileDownloader(
                Constants.JAR_URL,
                Constants.getJarPath());
        downloader.run();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JarFile jar = new JarFile(Constants.getJarPath());
                    Archive<ClassNode> archive = new JarArchive(jar);
                    ClassLoader classLoader = GameLoader.this.classLoader = new ArchiveClassLoader(archive);

                    Class<?> clientLoader = classLoader.loadClass("ClientLoader");
                    clientLoader.getConstructor(String.class, boolean.class).newInstance("1", true);
                    final JFrame frame = getJFrame(clientLoader, "add");
                    frame.setTitle("Mo Money Mo Pussy");
                    frame.revalidate();
                    buttonPanel = new ButtonPanel();
                    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
                    frame.pack();
                    setFrame(clientLoader, "add", frame);
                    engine.setGameJFrame(frame);
                } catch (IOException | NoSuchFieldException | InstantiationException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private JFrame getJFrame(Class clazz, String field) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(field);
        f.setAccessible(true);
        return (JFrame) f.get(null);

    }

    private void setFrame(Class clazz, String field, Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(field);
        f.setAccessible(true);
        f.set(null, obj);
    }

    private void invokeMain(Class c) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method main = c.getMethod("main", String[].class);
        String[] params = null; // init params accordingly
        main.invoke(null, (Object) params);
    }
}