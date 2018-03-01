package org.ubot.client.classloader;

/**
 * Created by Ethan on 7/11/2017.
 */

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.ubot.bot.component.RSCanvas;
import org.ubot.util.injection.Injector;
import org.ubot.util.injection.asm.ModifyCanvas;

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

	public ASMClassLoader(final ClassArchive classArchive, final List<Injector> injectorList) {
		this.classCache = new HashMap<>();
		this.classArchive = classArchive;
		this.injectorList = injectorList;
	}

	@Override
	protected URL findResource(String name) {
		if (getSystemResource(name) == null) {
			if (classArchive.containsResource(name)) {
				try {
					return classArchive.getResources().get(name).toURI().toURL();
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
		if (injectorList != null) {
			for (Injector injector : injectorList) {
				if (injector.canRun(node)) {
					injector.run(node);
				}
			}
		}
			new ModifyCanvas(RSCanvas.class.getCanonicalName().replaceAll("\\.", "/"), node);

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		final String className = name.replace('.', '/');
		if (classCache.containsKey(className)) {
			return classCache.get(className);
		} else if (classArchive.containsClass(className)) {
			final ClassNode node = classArchive.getClasses().get(className);
			if (node != null) {
				modify(node);
				classArchive.getClasses().remove(className);
				Class<?> clazz = nodeToClass(node);
				classCache.put(className, clazz);
				return clazz;
			}
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

