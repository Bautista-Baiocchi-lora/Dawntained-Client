package org.ubot.client.provider;

import org.ubot.classloader.ClassArchive;
import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;

public class ServerProvider {

	private final ServerLoader loader;
	private final ServerManifest manifest;
	private final ClassArchive classArchive;
	private final Class<?> mainClass;

	public ServerProvider(ServerManifest manifest, ServerLoader loader, ClassArchive classArchive, Class<?> mainClass) {
		this.manifest = manifest;
		this.loader = loader;
		this.classArchive = classArchive;
		this.mainClass = mainClass;
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

	public Class<?> getMainClass() {
		return mainClass;
	}

	@Override
	public String toString() {
		return manifest.serverName();
	}


}
