package org.ubot.bot;

import org.ubot.bot.component.RSCanvas;
import org.ubot.bot.component.screen.ScreenOverlay;
import org.ubot.bot.script.ScriptHandler;
import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.script.types.Script;
import org.ubot.classloader.ASMClassLoader;
import org.ubot.classloader.ClassArchive;
import org.ubot.client.Client;
import org.ubot.client.account.Account;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.ui.screens.BotConfigurationScreen;
import org.ubot.client.ui.screens.BotLoadingScreen;
import org.ubot.client.ui.screens.BotScreen;

import javax.swing.*;
import java.applet.Applet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bot {

	private final Client client;
	private String name;
	private Account account;
	private JPanel view;
	private BotCore core;
	private ScriptHandler scriptHandler;

	public Bot(Client client, String name) {
		this.client = client;
		this.name = name;
		this.scriptHandler = new ScriptHandler(this);
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public List<ScreenOverlay> getScreenOverlays() {
		if (core == null) {
			return new ArrayList<>();
		}
		return core.getScreenOverlays();
	}

	public boolean isGameLoaded() {
		if (view != null) {
			return view instanceof BotScreen;
		}
		return false;
	}

	public String getBotName() {
		return name;
	}

	public JPanel getView() {
		return view;
	}

	public RSCanvas getGameCanvas() {
		if (core == null) {
			return null;
		}
		return core.getGameCanvas();
	}

	public Applet getApplet() {
		if (core == null) {
			return null;
		}
		return core.getApplet();
	}

	public boolean scriptRunning() {
		return scriptHandler.getScriptState() == ScriptHandler.State.RUNNING;
	}

	public void startScript(ScriptData scriptData) {
		Script script = null;
		ClassArchive providerArchive = null;
		ASMClassLoader providerClassLoader = null;

		ClassArchive classArchive = new ClassArchive();
		classArchive.addJar(scriptData.getScriptPath());

		for (Map.Entry<File, ServerProvider> providerEntry : client.getModel().getProviders().entrySet()) {
			if (providerEntry.getValue().getManifest().serverName().equals(this.getServerName())) {
				providerArchive = providerEntry.getValue().getClassArchive();
				providerClassLoader = providerEntry.getValue().getClassLoader();
			}
		}

		classArchive.inheritClassArchive(this.getClassArchive());
		classArchive.inheritClassArchive(providerArchive);

		ASMClassLoader classLoader = new ASMClassLoader(classArchive);
		classLoader.inheritClassLoader(core.getClassLoader());
		classLoader.inheritClassLoader(providerClassLoader);

		try {
			script = (Script) classLoader.loadClass(scriptData.getMainClass().getCanonicalName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		scriptHandler.start(script, scriptData);
	}

	public void pauseScript() {
		scriptHandler.pause();
	}

	public void stopScript() {
		scriptHandler.stop();
	}

	public void launch(BotCore core) {
		this.core = core;
		this.view = new BotScreen(this);
		client.displayScreen(this);
	}

	public void initiateConfiguration(ArrayList<ServerProvider> providers) {
		this.view = new BotConfigurationScreen(this, providers);
	}

	public void initiateServerLoader(ServerProvider provider) {
		final BotLoadingScreen loadingScreen = new BotLoadingScreen(this, provider);
		this.view = loadingScreen;
		client.displayScreen(this);
		loadingScreen.run();
	}

	public void destroy() {
		if (scriptRunning()) {
			stopScript();
		}
		if (getApplet() != null) {
			getApplet().stop();
			getApplet().destroy();
		}
	}

	public String getServerName() {
		return core.getServerName();
	}
	
	public ClassArchive getClassArchive() {
		return core.getClassArchive();
	}

}
