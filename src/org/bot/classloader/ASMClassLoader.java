package org.bot.classloader;

/**
 * Created by Ethan on 7/11/2017.
 */

import org.bot.Engine;
import org.bot.component.RSCanvas;
import org.bot.provider.manifest.Revision;
import org.bot.util.injection.Injector;
import org.bot.util.injection.asm.ModifyCanvas;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

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

	public Map<String, Class<?>> classCache;
	public ClassArchive classArchive;
	private List<Injector> injectorList;
	private Injector[] injectors;

	public ASMClassLoader(final ClassArchive classArchive) {
		this.classCache = new HashMap<>();
		this.classArchive = classArchive;
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

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return findClass(name);
	}

	private void modify(ClassNode node) {
		if (Engine.getServerProvider() != null) {
			injectorList = Engine.getServerProvider().getLoader().getInjectables();
			injectors = injectorList.toArray(new Injector[injectorList.size()]);
			for (Injector injector : injectors) {
				if (injector.canRun(node)) {
					injector.run(node);
				}
			}
		}
		if (Engine.getServerProvider() != null) {
			if (Engine.getServerProvider().getManifest().revision() == Revision.OSRS) {
				new ModifyCanvas(RSCanvas.class.getCanonicalName().replaceAll("\\.", "/"), node);
			}
		}


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
			modify(node);
			classArchive.classes.remove(key);
			Class<?> c = nodeToClass(node);
			classCache.put(key, c);
			return c;
		}
		return getSystemClassLoader().loadClass(name);
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

