package org.bot.classloader;

import org.bot.util.directory.DirectoryManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Ethan on 7/11/2017.
 */
public class ClassArchive {
	public final ArrayList<String> classNames;
	public final HashMap<String, ClassNode> classes;
	public final Map<String, File> resources;
	public URL lastParsed;
	private ArrayList<URL> jarFiles;


	public ClassArchive() {
		this.classNames = new ArrayList<>();
		this.classes = new HashMap<>();
		this.resources = new HashMap<>();
		this.jarFiles = new ArrayList<>();
	}

	protected void loadClass(InputStream in) throws IOException {
		ClassReader cr = new ClassReader(in);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);

		classNames.add(cn.name.replace('/', '.'));
		classes.put(cn.name, cn);

	}

	public void loadResource(final String name, final InputStream in) throws IOException {
		String path;
		path = DirectoryManager.SERVER_JARS_PATH + File.separator;
		File f1 = new File(path);

		final File f = File.createTempFile("bot", ".tmp", f1);
		f.deleteOnExit();
		try (OutputStream out = new FileOutputStream(f)) {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
		}
		resources.put(name, f);
	}

	public void addJar(final File file) {
		try {
			addJar(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addJar(final URL url) {
		this.lastParsed = url;
		try {
			addJar(url.openConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addJar(final URLConnection connection) {
		try {
			final ZipInputStream zin = new ZipInputStream(connection.getInputStream());
			ZipEntry e;
			while ((e = zin.getNextEntry()) != null) {
				if (e.isDirectory())
					continue;
				if (e.getName().endsWith(".class")) {
					loadClass(zin);
				} else {
					loadResource(e.getName(), zin);
				}

			}
			zin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}