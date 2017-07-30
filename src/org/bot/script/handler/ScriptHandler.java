package org.bot.script.handler;

import org.bot.Engine;
import org.bot.component.listeners.PaintListener;
import org.bot.script.scriptdata.ScriptData;
import org.bot.script.types.Script;
import org.bot.ui.screens.clientframe.menu.logger.LogType;
import org.bot.ui.screens.clientframe.menu.logger.Logger;
import org.bot.util.Condition;

/**
 * Created by Ethan on 7/14/2017.
 */
public class ScriptHandler implements Runnable {
	private Thread scriptThread;
	private Script script;
	private ScriptData scriptData;
	private volatile State scriptState = State.STOPPED;
	private long breakDuration;
	private PaintListener paintListener;

	@Override
	public void run() {
		try {
			while (!scriptState.equals(State.STOPPED)) {
				if (script == null) {
					stop();
				} else if (scriptState.equals(State.PAUSE)) {
					Condition.sleep(500);
				} else if (scriptState.equals(State.BREAKING)) {
					this.script.onBreak();
					Condition.sleep(breakDuration);
				} else {
					int timeToSleep = script.operate();
					Condition.sleep(timeToSleep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(Script script, ScriptData scriptData) {
		if (script == null) {
			Logger.logException("Error starting script.", LogType.CLIENT);
			return;
		}
		Logger.log("Script Started: " + scriptData.name);
		this.scriptState = State.RUNNING;
		this.scriptData = scriptData;
		this.script = script;
		this.scriptThread = new Thread(this);
		if (this.script.onStart()) {
			this.scriptThread.start();
			if (script instanceof PaintListener) {
				paintListener = (PaintListener) script;
				Engine.getGameCanvas().getPaintListeners().add(paintListener);
			}
		}
	}

	public void takeBreak(long duration) {
		Logger.logWarning(scriptData.name + " Breaking for: " + duration);
		this.breakDuration = duration;
		this.scriptState = State.BREAKING;
	}

	public void stop() {
		Logger.logWarning("Script Stopped: " + scriptData.name);
		this.scriptState = State.STOPPED;
		this.script.onStop();
		this.scriptThread.interrupt();
		Engine.getGameCanvas().getPaintListeners().remove(paintListener);
		this.script = null;
		this.scriptThread = null;
		this.paintListener = null;
	}

	public void pause() {
		Logger.logWarning("Script Paused: " + scriptData.name);
		this.scriptState = State.PAUSE;
	}

	public State getScriptState() {
		return scriptState;
	}

	public void setScriptState(State scriptState) {
		this.scriptState = scriptState;
	}

	public enum State {
		RUNNING, BREAKING, PAUSE, STOPPED
	}
}