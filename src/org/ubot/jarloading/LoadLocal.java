package org.ubot.jarloading;


import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.provider.manifest.ServerManifest;
import org.ubot.util.directory.DirectoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by Ethan on 3/2/2018.
 */
public class LoadLocal {
    private boolean server = true;
    private String directory = "Server Providers";
    public LoadLocal(boolean server) {
        if(!server) {
            server = false;
            directory = "Scripts";
            //manifest = ScriptManifest.class
        }
        loadLocalJars();
    }
    public void loadLocalJars() {
        try {
            for (File file : DirectoryManager.getInstance().getRootDirectory().getSubDirectory(directory)
                    .getFiles()) {
                final ClassArchive classArchive = new ClassArchive();
                if (file.getAbsolutePath().endsWith(".jar")) {
                    classArchive.addJar(file);
                    final ASMClassLoader classLoader = new ASMClassLoader(classArchive);
                    try (JarInputStream inputStream = new JarInputStream(new FileInputStream(file))) {
                        JarEntry jarEntry;
                        while ((jarEntry = inputStream.getNextJarEntry()) != null) {
                            if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
                                System.out.println(jarEntry.getName());
                                String classPackage = jarEntry.getName().replace(".class", "");
                                Class<?> clazz = classLoader.loadClass(classPackage.replaceAll("/", "."));
                                if(server) {
                                    if (clazz.isAnnotationPresent(ServerManifest.class)) {
                                        final ServerManifest manifest = clazz.getAnnotation(ServerManifest.class);
                                        System.out.println("Server: "+manifest.serverName());
                                    }
                                } else {
                                    //handle scripts
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
