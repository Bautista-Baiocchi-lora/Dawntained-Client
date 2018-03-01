package org.ubot.bot;

import org.ubot.bot.component.RSCanvas;
import org.ubot.bot.script.handler.ScriptHandler;
import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.bot.ui.scriptselector.ScriptSelector;
import org.ubot.client.account.Account;
import org.ubot.client.classloader.ASMClassLoader;
import org.ubot.client.classloader.ClassArchive;
import org.ubot.client.provider.ServerProvider;
import org.ubot.client.provider.inputs.InternalKeyboard;
import org.ubot.client.provider.inputs.InternalMouse;
import org.ubot.util.reflection.ReflectionEngine;

import java.awt.*;

public class BotModel {

	private final Account account;
	private final ClassArchive classArchive;
	private final ReflectionEngine reflectionEngine;
	private final ASMClassLoader classLoader;
	private final Component component;
	private final Bot bot;
	private RSCanvas gameCanvas;
	private final ScriptHandler scriptHandler;
	private ScriptSelector scriptSelector;
	private boolean debugMouse, debugNPCs, debugObjects, debugPlayers, debugInventory, debugGame;
	private final InternalMouse mouse;
	private final InternalKeyboard keyboard;


	private BotModel(Builder builder) {
		this.account = builder.account;
		this.classLoader = builder.classLoader;
		this.reflectionEngine = builder.reflectionEngine;
		this.classArchive = builder.classArchive;
		this.component = builder.component;
		this.mouse = builder.mouse;
		this.keyboard = builder.keyboard;
		this.bot = builder.bot;
		this.scriptHandler = new ScriptHandler(this);
	}

	public RSCanvas getGameCanvas() {
		return gameCanvas;
	}

	public Component getComponent() {
		return component;
	}

	public String getUsername() {
		return account.getUsername();
	}

	public String getServer() {
		return account.getServer();
	}

	public void changeScriptState(ScriptHandler.State state) {
		switch (state) {
			case RUN:
				if (scriptHandler.getScriptState().equals(ScriptHandler.State.PAUSE)) {
					scriptHandler.setScriptState(state);
					Logger.log("Script started.", LogType.CLIENT);
				} else if (scriptSelector == null || !scriptSelector.isVisible()) {
					scriptSelector = new ScriptSelector(this);
				}
				break;
			case PAUSE:
				if (scriptHandler.getScriptState().equals(ScriptHandler.State.RUN)) {
					scriptHandler.setScriptState(ScriptHandler.State.PAUSE);
					Logger.log("Script Paused.", LogType.CLIENT);
				}
				break;
			case STOP:
				if (scriptHandler.getScriptState().equals(ScriptHandler.State.RUN) || scriptHandler.getScriptState().equals(ScriptHandler.State.PAUSE)) {
					scriptHandler.setScriptState(ScriptHandler.State.STOP);
					Logger.log("Script Stopped.", LogType.CLIENT);
				}
				break;
		}
	}

	public void startScript(ScriptData scriptData) {
		scriptHandler.start(null, scriptData);
	}

	public void toggleMouseDebug() {
		this.debugMouse = !debugMouse;
	}

	public void toggleNPCDebug() {
		this.debugNPCs = !debugNPCs;
	}

	public void toggleObjectDebug() {
		this.debugObjects = !debugObjects;
	}

	public void togglePlayersDebug() {
		this.debugPlayers = !debugPlayers;
	}

	public void toggleInventoryDebug() {
		this.debugInventory = !debugInventory;
	}

	public void toggleGameDebug() {
		this.debugGame = !debugGame;
	}
	public void setGameCanvas(RSCanvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}
	public static class Builder {

		private static Account account;
		private static ClassArchive classArchive;
		private static ASMClassLoader classLoader;
		private static ReflectionEngine reflectionEngine;
		private static InternalKeyboard keyboard;
		private static InternalMouse mouse;
		private static Bot bot;
		private static Component component;

		public BotModel.Builder setClassArchive(ClassArchive classArchive) {
			this.classArchive = classArchive;
			return this;
		}

		public BotModel.Builder setASMClassLoader(ASMClassLoader classLoader) {
			this.classLoader = classLoader;
			return this;
		}

		public BotModel.Builder setReflectionEngine(ReflectionEngine reflectionEngine) {
			this.reflectionEngine = reflectionEngine;
			return this;
		}

		public BotModel.Builder setGameComponent(Component component) {
			this.component = component;
			return this;
		}

		public BotModel.Builder setInternalKeyboard(InternalKeyboard keyboard) {
			this.keyboard = keyboard;
			return this;
		}

		public BotModel.Builder setInternalMouse(InternalMouse mouse) {
			this.mouse = mouse;
			return this;
		}

		public BotModel.Builder setAccount(Account account) {
			this.account = account;
			return this;
		}

		public BotModel build(Bot bot) {
			return new BotModel(this);
		}

	}
}
