package org.ubot.client.provider;

import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;

public class ServerProvider {

	private final ServerLoader loader;
	private final ServerManifest manifest;
	private final ClassArchive classArchive;
	private final ASMClassLoader classLoader;

	public ServerProvider(ServerManifest manifest, ServerLoader loader, ClassArchive classArchive, ASMClassLoader classLoader) {
		this.manifest = manifest;
		this.loader = loader;
		this.classArchive = classArchive;
		this.classLoader = classLoader;
	}

	public ServerLoader getLoader() {
		return loader;
	}

	public ServerManifest getManifest() {
		return manifest;
	}

	public ClassArchive getClassArchive() {
		return classArchive;
	}

	public ASMClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public String toString() {
		return manifest.serverName();
	}


}
