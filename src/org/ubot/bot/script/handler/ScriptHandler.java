package org.ubot.bot.script.handler;

import org.ubot.bot.BotModel;
import org.ubot.bot.script.scriptdata.ScriptData;
import org.ubot.bot.script.types.Script;
import org.ubot.bot.ui.logger.LogType;
import org.ubot.bot.ui.logger.Logger;
import org.ubot.bot.component.RSCanvas;
import org.ubot.bot.component.listeners.PaintListener;
import org.ubot.util.Condition;

/**
 * Created by Ethan on 7/14/2017.
 */
public class ScriptHandler implements Runnable {
	private Thread scriptThread;
	private Script script;
	private ScriptData scriptData;
	private volatile State scriptState;
	private long breakDuration;
	private PaintListener paintListener;
	private BotModel botModel;
	public ScriptHandler(BotModel botModel) {
		this.botModel = botModel;
		this.scriptState = State.STOP;
	}

	@Override
	public void run() {
		try {
			while (!scriptState.equals(State.STOP)) {
				if (script == null) {
					stop();
				} else if (scriptState.equals(State.PAUSE)) {
					Condition.sleep(500);
				} else if (scriptState.equals(State.BREAK)) {
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
		Logger.log("Script Started: " + scriptData.name, LogType.CLIENT);
		this.scriptState = State.RUN;
		this.scriptData = scriptData;
		this.script = script;
		this.scriptThread = new Thread(this);
		if (this.script.onStart()) {
			this.scriptThread.start();
			if (script instanceof PaintListener) {
				botModel.getGameCanvas().getPaintListeners().add((PaintListener) script);
			}
		}
	}

	private void takeBreak(long duration) {
		Logger.logWarning(scriptData.name + " Breaking for: " + duration, LogType.CLIENT);
		this.breakDuration = duration;
		this.scriptState = State.BREAK;
	}

	private void stop() {
		Logger.logWarning("Script Stopped: " + scriptData.name, LogType.CLIENT);
		this.scriptState = State.STOP;
		this.script.onStop();
		this.scriptThread.interrupt();
		botModel.getGameCanvas().getPaintListeners().remove(paintListener);
		this.script = null;
		this.scriptThread = null;
		this.paintListener = null;
	}

	private void pause() {
		Logger.logWarning("Script Paused: " + scriptData.name, LogType.CLIENT);
		this.scriptState = State.PAUSE;
	}

	public State getScriptState() {
		return scriptState;
	}

	public void setScriptState(State scriptState) {
		this.scriptState = scriptState;
	}

	public enum State {
		RUN, BREAK, PAUSE, STOP
	}
}