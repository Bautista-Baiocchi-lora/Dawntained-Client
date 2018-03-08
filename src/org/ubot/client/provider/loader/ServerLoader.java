package org.ubot.client.provider.loader;

import org.ubot.bot.BotCore;
import org.ubot.bot.component.RSCanvas;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.util.Condition;
import org.ubot.util.FileDownloader;
import org.ubot.util.directory.DirectoryManager;
import org.ubot.util.injection.Injector;
import org.ubot.util.reflection.ReflectionEngine;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ServerLoader extends SwingWorker<BotCore, BotCore> {

	private final String serverName, jarUrl, hookUrl;
	private Applet applet;
	private RSCanvas canvas;

	public ServerLoader(String jarUrl, String hookUrl, String serverName) {
		this.serverName = serverName;
		this.jarUrl = jarUrl;
		this.hookUrl = hookUrl;
	}

	@Override
	protected BotCore doInBackground() throws Exception {
		final BotCore core = new BotCore(serverName);
		setProgress(10);
		final FileDownloader downloader = new FileDownloader(jarUrl, DirectoryManager.SERVER_JARS_PATH, serverName);
		final Thread downloadThread = new Thread(downloader);
		downloadThread.start();
		while (downloadThread.isAlive()) {
			setProgress((int) (40 * downloader.getProgress()));
		}
		loadHooks(hookUrl);
		setProgress(50);
		final ClassArchive classArchive = new ClassArchive();
		classArchive.addJar(downloader.getDownloadedFile());
		setProgress(60);
		final ASMClassLoader asmClassLoader = new ASMClassLoader(classArchive, getInjectables());
		core.setClassLoader(asmClassLoader);
		setProgress(70);
		final ReflectionEngine reflectionEngine = new ReflectionEngine(asmClassLoader);
		core.setReflectionEngine(reflectionEngine);
		setProgress(80);
		final Applet applet = loadApplet(reflectionEngine);
		applet.setPreferredSize(new Dimension(765, 503));
		this.applet = applet;
		setProgress(85);
		core.setApplet(applet);
		setProgress(90);
		while ((canvas = getCanvas()) == null) {
			Condition.sleep(100);
		}
		core.setGameCanvas(canvas);
		SwingUtilities.invokeLater(() -> {
			try {
				core.setScreenOverlays(getOverlays());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setProgress(100);
		core.setClassArchive(classArchive);
		return core;
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


	private synchronized RSCanvas getCanvas() {
		if (canvas != null) {
			return canvas;
		}
		if (applet == null || applet.getComponentCount() == 0 || !(applet.getComponent(0) instanceof RSCanvas)) {
			return null;
		}
		return (RSCanvas) applet.getComponent(0);
	}

}
