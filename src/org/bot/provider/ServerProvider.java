package org.bot.provider;

import org.bot.component.screen.ScreenOverlay;
import org.bot.provider.loader.ServerLoader;
import org.bot.provider.manifest.ServerManifest;

import java.util.List;

public class ServerProvider {

	private final ServerLoader<?> loader;
	private final ServerManifest manifest;
	private final List<ScreenOverlay> screenOverlays;
	private boolean debugMouse, debugNPCs, debugPlayers, debugInventory, debugGameInfo;

	public ServerProvider(ServerLoader<?> loader, ServerManifest manifest) {
		this.loader = loader;
		this.manifest = manifest;
		this.screenOverlays = loader.getOverlays();
	}

	public ServerManifest getManifest() {
		return manifest;
	}

	public ServerLoader<?> getLoader() {
		return loader;
	}

	public List<ScreenOverlay> getScreenOverlays() {
		return screenOverlays;
	}

	public void debugMouse(boolean debug) {
		debugMouse = debug;
	}

	public boolean isDebugMouse() {
		return debugMouse;
	}

	public void debugNPCs(boolean debug) {
		debugNPCs = debug;
	}

	public boolean isDebugNPCs() {
		return debugNPCs;
	}

	public void debugPlayers(boolean debug) {
		this.debugNPCs = debug;
	}

	public boolean isDebugPlayers() {
		return debugPlayers;
	}

	public void debugInventory(boolean debug) {
		this.debugInventory = debug;
	}

	public boolean isDebugInventory() {
		return debugInventory;
	}

	public void debugGameInfo(boolean debug) {
		this.debugGameInfo = debug;
	}

	public boolean isDebugGameInfo() {
		return debugGameInfo;
	}

}
