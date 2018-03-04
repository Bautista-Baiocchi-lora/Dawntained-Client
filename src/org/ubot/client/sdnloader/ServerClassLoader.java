package org.ubot.client.sdnloader;

import org.ubot.client.provider.manifest.ServerManifest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ethan on 3/2/2018.
 */
public class ServerClassLoader extends ClassLoader {
    public final AtomicReference<Class<?>> classAtomicReference;
    private final HashMap<String, byte[]> scriptByteMap;
    private final HashMap<String, Class<?>> scriptClassMap;
    private final HashMap<String, byte[]> resourceMap;

    public ServerClassLoader(HashMap<String, byte[]> scriptByteMap, HashMap<String, byte[]> resourceMap) {
        this.scriptClassMap = new HashMap<>();
        this.scriptByteMap = scriptByteMap;
        this.classAtomicReference = new AtomicReference<>();
        this.resourceMap = resourceMap;
        for (Map.Entry<String, byte[]> entry : scriptByteMap.entrySet()) {
            if (entry.getKey().endsWith(".class")) {
                String className = entry.getKey().replace('/', '.').replaceAll(".class", "");
                try {
                    final Class<?> loadClass;
                    if ((loadClass = this.loadClass(className)) == null || loadClass.getAnnotation(ServerManifest.class) == null) {
                        continue;
                    }
                    System.out.println(loadClass.getName());
                    classAtomicReference.set(loadClass);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    @Override
    public InputStream getResourceAsStream(String resource) {
        if (!this.resourceMap.containsKey(resource)) {
            return super.getResourceAsStream(resource);
        }
        byte[] resources = this.resourceMap.get(resource);
        return new ByteArrayInputStream(resources);
    }


    @Override
    public Class<?> loadClass(final String className) throws ClassNotFoundException {
        if (this.scriptClassMap.containsKey(className)) {
            return this.scriptClassMap.get(className);
        }
        if (!this.scriptByteMap.containsKey(new StringBuilder().insert(0, className.replace('.', '/')).append(".class").toString())) {
            return super.loadClass(className);
        }
        final byte[] array = this.scriptByteMap.get(new StringBuilder().insert(0, className.replace('.', '/')).append(".class").toString());
        final int n = 0;
        final byte[] array2 = array;
        final Class<?> defineClass = super.defineClass(className, array2, n, array2.length);
        final Map<String, Class<?>> classMap = this.scriptClassMap;
        final Class<?> clazz = defineClass;
        classMap.put(className, clazz);
        return clazz;
    }
}
