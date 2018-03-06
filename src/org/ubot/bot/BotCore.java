package org.ubot.bot;

import org.ubot.component.RSCanvas;
import org.ubot.util.reflection.ReflectionEngine;

import java.applet.Applet;

public class BotCore {

	private final String serverName;
	private ReflectionEngine reflectionEngine;
	private Applet applet;
	private RSCanvas gameCanvas;

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
	}

	public ReflectionEngine getReflectionEngine() {
		return reflectionEngine;
	}

	public void setReflectionEngine(final ReflectionEngine reflectionEngine) {
		this.reflectionEngine = reflectionEngine;
	}

	public RSCanvas getGameCanvas() {
		return gameCanvas;
	}

	public void setGameCanvas(final RSCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}
}
