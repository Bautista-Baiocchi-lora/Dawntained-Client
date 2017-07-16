package org.bot.script.handler;

import org.bot.Engine;
import org.bot.component.listeners.PaintListener;
import org.bot.script.scriptdata.ScriptData;
import org.bot.script.types.LoopScript;
import org.bot.util.Condition;

/**
 * Created by Ethan on 7/14/2017.
 */
public class ScriptHandler implements Runnable {
	private Thread scriptThread;
	private LoopScript script;
	private ScriptData scriptData;
	private State scriptState = State.STOPPED;
	private PaintListener paintListener;

	@Override
	public void run() {
		try {
			while (!scriptState.equals(State.STOPPED)) {
				if (script == null) {
					stop();
				} else if (scriptState.equals(State.PAUSE)) {
					Condition.sleep(500);
				} else {
					int timeToSleep = script.operate();
					Condition.sleep(timeToSleep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(LoopScript script, ScriptData scriptData) {
		if (script == null)
			return;

		System.out.println("Script Started: " + scriptData.name);
		this.scriptState = State.RUNNING;
		this.scriptData = scriptData;
		this.script = script;
		this.scriptThread = new Thread(this);
		this.script.onStart();
		this.scriptThread.start();

		if (script instanceof PaintListener) {
			paintListener = (PaintListener) script;
			Engine.getGameCanvas().getPaintListeners().add(paintListener);
		}

	}

	public void stop() {
		System.out.println("Script Stopped: " + scriptData.name);
		this.scriptState = State.STOPPED;
		this.script.onStop();
		this.scriptThread.interrupt();
		Engine.getGameCanvas().getPaintListeners().remove(paintListener);
		this.script = null;
		this.scriptThread = null;
		this.paintListener = null;

	}

	public void pause() {
		System.out.println("Script Paused: " + scriptData.name);
		this.scriptState = State.PAUSE;
	}

	public void renew() {
		this.scriptThread = new Thread(this);
	}

	public State getScriptState() {
		return scriptState;
	}

	public void setScriptState(State scriptState) {
		this.scriptState = scriptState;
	}

	public Thread getScriptThread() {
		return scriptThread;
	}

	public enum State {
		RUNNING, PAUSE, STOPPED
	}
}