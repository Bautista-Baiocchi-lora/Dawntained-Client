package org.ubot.classloader;

/**
 * Created by Ethan on 7/11/2017.
 */


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.ubot.bot.component.RSCanvas;
import org.ubot.util.injection.Injector;
import org.ubot.util.injection.injections.ModifyCanvas;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASMClassLoader extends ClassLoader {

	private final Map<String, Class<?>> classCache;
	private final ClassArchive classArchive;
	private final List<Injector> injectorList;

	public ASMClassLoader(final ClassArchive classArchive, List<Injector> injectors) {
		this.classCache = new HashMap<>();
		this.classArchive = classArchive;
		this.injectorList = injectors;
	}

	public ASMClassLoader(final ClassArchive classArchive) {
		this(classArchive, null);
	}

	@Override
	protected URL findResource(String name) {
		if (getSystemResource(name) == null) {
			if (classArchive.resources.containsKey(name)) {
				try {
					return classArchive.resources.get(name).toURI().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		}
		return getSystemResource(name);
	}

	public void inheritClassLoader(ASMClassLoader classLoader) {
		if (classLoader == null)
			return;
		inheritClassCache(classLoader);
	}

	private void inheritClassCache(ASMClassLoader classLoader) {
		for (Map.Entry<String, Class<?>> classNodes : classLoader.classCache.entrySet()) {
			if (classCache.containsKey(classNodes.getKey())) {
				classCache.remove(classNodes.getKey());
				classCache.put(classNodes.getKey(), classNodes.getValue());
				System.err.println("Removed: " + classNodes.getKey());
			} else {
				classCache.put(classNodes.getKey(), classNodes.getValue());
				System.out.println("added cache: " + classNodes.getKey());
			}
		}
	}
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return findClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return getSystemClassLoader().loadClass(name);
		} catch (Exception ignored) {

		}
		String key = name.replace('.', '/');
		if (classCache.containsKey(key)) {
			return classCache.get(key);
		}
		ClassNode node = classArchive.classes.get(key);
		if (node != null) {
			classArchive.classes.remove(key);
			Class<?> c = nodeToClass(inject(node));
			classCache.put(key, c);
			return c;
		}
		return getSystemClassLoader().loadClass(name);
	}

	private ClassNode inject(ClassNode node) {
		if (injectorList != null) {
			for (Injector injector : injectorList) {
				if (injector.condition(node)) {
					injector.inject(node);
					return node;
				}
			}
		}
		new ModifyCanvas(RSCanvas.class.getCanonicalName().replaceAll("\\.", "/"), node);
		return node;
	}

	private final Class<?> nodeToClass(ClassNode node) {
		if (super.findLoadedClass(node.name) != null) {
			return findLoadedClass(node.name);
		}
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		node.accept(cw);
		byte[] b = cw.toByteArray();
		return defineClass(node.name.replace('/', '.'), b, 0, b.length,
				getDomain());
	}

	private final ProtectionDomain getDomain() {
		CodeSource code = null;
		try {
			code = new CodeSource(new URL("http://127.0.0.1"), (Certificate[]) null);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new ProtectionDomain(code, getPermissions());
	}

	private final Permissions getPermissions() {
		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		return permissions;
	}

}

