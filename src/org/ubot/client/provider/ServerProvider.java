package org.ubot.client.provider;

import org.ubot.client.provider.loader.ServerLoader;
import org.ubot.client.provider.manifest.ServerManifest;

public class ServerProvider {

	private final ServerLoader loader;
	private final ServerManifest manifest;

	public ServerProvider(ServerManifest manifest, ServerLoader loader) {
		this.manifest = manifest;
		this.loader = loader;
	}

	public ServerLoader getLoader() {
		return loader;
	}

	public ServerManifest getManifest() {
		return manifest;
	}

	@Override
	public String toString() {
		return manifest.serverName();
	}


}
