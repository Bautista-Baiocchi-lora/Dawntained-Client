package org.ubot.jarloading;

import org.ubot.provider.loader.ServerClassLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * Created by Ethan on 3/2/2018.
 */
public class SDNDownloader {

    public synchronized Class<?> getSDNJar(String link, boolean server) {
        try {
            byte[] bytes = getByteArray(link);
            final HashMap<String, byte[]> classMap = new HashMap<>();
            final HashMap<String, byte[]> resourceMap = new HashMap<>();
            final byte[] array = new byte[1024];
            final JarInputStream jarInputStream = new JarInputStream(new ByteArrayInputStream(bytes));
            ZipEntry nextEntry;
            while ((nextEntry = jarInputStream.getNextEntry()) != null) {
                final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JarInputStream jarInputStream2 = jarInputStream;
                int read;
                while ((read = jarInputStream2.read(array, 0, array.length)) != -1) {
                    jarInputStream2 = jarInputStream;
                    byteArrayOutputStream.write(array, 0, read);
                }
                if (nextEntry.getName().endsWith(".class")) {
                    classMap.put(nextEntry.getName(), byteArrayOutputStream.toByteArray());
                } else {
                    resourceMap.put(nextEntry.getName(), byteArrayOutputStream.toByteArray());
                }
            }
            if (server) {
                return (new ServerClassLoader(classMap, resourceMap).classAtomicReference.get());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String link) {
        try {
            URL url = new URL(link);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int read;
            while ((read = dataInputStream.read()) != -1) {
                byteArrayOutputStream.write(read);
            }
            dataInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
