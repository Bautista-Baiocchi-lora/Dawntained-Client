package org.ubot.client.provider.loader;

import org.ubot.bot.BotModel;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.component.RSCanvas;
import org.ubot.component.screen.ScreenOverlay;
import org.ubot.util.Condition;
import org.ubot.util.FileDownloader;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerLoader extends SwingWorker<BotModel.Builder, BotModel.Builder> {

	private final String serverName, jarUrl, hookUrl;
	private Applet applet;
	private RSCanvas canvas;

	public ServerLoader(String jarUrl, String hookUrl, String serverName) {
		this.serverName = serverName;
		this.jarUrl = jarUrl;
		this.hookUrl = hookUrl;
	}

	@Override
	protected BotModel.Builder doInBackground() throws Exception {
		final BotModel.Builder builder = new BotModel.Builder(serverName);
		setProgress(10);
		final FileDownloader downloader = new FileDownloader(jarUrl, serverName);
		final Thread downloadThread = new Thread(downloader);
		downloadThread.start();
		while (downloadThread.isAlive()) {
			setProgress((int) (40 * downloader.getProgress()));
		}
		loadHooks(hookUrl);
		setProgress(50);
		final ClassArchive classArchive = new ClassArchive();
		classArchive.addJar(new File(downloader.getArchivePath() + "/" + serverName + ".jar"));
		setProgress(60);
		final ASMClassLoader asmClassLoader = new ASMClassLoader(classArchive, getInjectables());
		setProgress(70);
		final ReflectionEngine reflectionEngine = new ReflectionEngine(asmClassLoader);
		builder.reflectionEngine(reflectionEngine);
		setProgress(80);
		final Applet applet = loadApplet(reflectionEngine);
		this.applet = applet;
		setProgress(85);
		builder.applet(applet);
		setProgress(90);
		while ((canvas = getCanvas()) == null) {
			Condition.sleep(100);
		}
		canvas.setServerLoader(this);
		setProgress(100);
		return builder;
	}

	private void loadHooks(String hookUrl) {
		//logic
	}

	public List<ScreenOverlay> getOverlays() {
		List<ScreenOverlay> overlays = new ArrayList<>();
		/*
			We can add forced overlays here if we wanted too.
		 */
		return overlays;
	}

	protected abstract List<Injector> getInjectables();

	protected abstract Applet loadApplet(ReflectionEngine reflectionEngine) throws IllegalAccessException;

	private Applet getApplet() {
		return this.applet;
	}

	private synchronized RSCanvas getCanvas() {
		if (canvas != null) {
			return canvas;
		}
		if (getApplet() == null || getApplet().getComponentCount() == 0 || !(getApplet().getComponent(0) instanceof RSCanvas)) {
			return null;
		}
		return (RSCanvas) getApplet().getComponent(0);
	}

}
