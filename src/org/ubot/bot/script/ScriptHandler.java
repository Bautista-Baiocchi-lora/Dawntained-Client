package org.ubot.bot.script;

import org.ubot.bot.Bot;
import org.ubot.bot.canvas.listeners.PaintListener;
import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.script.types.Script;
import org.ubot.client.ui.logger.Logger;
import org.ubot.util.Condition;

public class ScriptHandler implements Runnable {

	private Bot bot;
	private Thread scriptThread;
	private Script script;
	private ScriptData scriptData;
	private volatile State scriptState = State.STOPPED;
	private long breakDuration;
	private PaintListener paintListener;

	public ScriptHandler(Bot bot) {
		this.bot = bot;
	}

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
			Logger.logException("Error starting script.");
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
				bot.getGameCanvas().getPaintListeners().add(paintListener);
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
		bot.getGameCanvas().getPaintListeners().remove(paintListener);
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