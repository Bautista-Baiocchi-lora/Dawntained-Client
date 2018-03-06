package org.ubot.bot;

import org.ubot.bot.canvas.RSCanvas;
import org.ubot.bot.canvas.screen.ScreenOverlay;
import org.ubot.client.ui.logger.Logger;
import org.ubot.util.reflection.ReflectionEngine;

import java.applet.Applet;
import java.util.List;

public class BotCore {

	private final String serverName;
	private ReflectionEngine reflectionEngine;
	private Applet applet;
	private RSCanvas gameCanvas;
	private List<ScreenOverlay> screenOverlays;

	public List<ScreenOverlay> getScreenOverlays() {
		return screenOverlays;
	}

	public void setScreenOverlays(final List<ScreenOverlay> screenOverlays) {
		this.screenOverlays = screenOverlays;
		this.gameCanvas.setSetOverlays(screenOverlays);
		Logger.log("Screen overlays injected");
	}

	public BotCore(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public Applet getApplet() {
		return applet;
	}

	public void setApplet(final Applet applet) {
		this.applet = applet;
		Logger.log("Applet configured");
	}

	public ReflectionEngine getReflectionEngine() {
		return reflectionEngine;
	}

	public void setReflectionEngine(final ReflectionEngine reflectionEngine) {
		this.reflectionEngine = reflectionEngine;
		Logger.log("Reflection engine started");
	}

	public RSCanvas getGameCanvas() {
		return gameCanvas;
	}

	public void setGameCanvas(final RSCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
		Logger.log("Game canvas injected.");
	}
}
